package toy_project.mentor_pairing.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.*;
import toy_project.mentor_pairing.recommendation.MentorRecommendation;
import toy_project.mentor_pairing.repository.MentoringApplicationRepository;
import toy_project.mentor_pairing.repository.MentoringProgramRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MentoringProgramService {
    private final MentoringProgramRepository mentoringProgramRepository;
    private final MentoringApplicationRepository mentoringApplicationRepository;
    private final MentorRecommendationService mentorRecommendationService;

    @Transactional
    public Long match(Long menteeApplicationId, Long mentorApplicationId){
        //1. 멘티 신청 조회
        MentoringApplication menteeApplication = mentoringApplicationRepository.findByApplicationId(menteeApplicationId);
        //2. 멘토 신청 조회
        MentoringApplication mentorApplication = mentoringApplicationRepository.findByApplicationId(mentorApplicationId);
        //3. 두 신청이 존재하는지 확인
        if(menteeApplication == null) throw new IllegalArgumentException("멘티신청이 존재하지 않습니다.");
        if(mentorApplication == null) throw new IllegalArgumentException("멘토신청이 존재하지 않습니다.");
        //4. 각각 MENTEE, MENTOR 역할 확인
        if(menteeApplication.getRole()!= MentoringRole.MENTEE) throw new IllegalStateException("신청 역할과 멘티가 일치하지 않습니다.");
        if(mentorApplication.getRole()!= MentoringRole.MENTOR) throw new IllegalStateException("신청 역할과 멘토가 일치하지 않습니다.");
        //5. 두 신청 모두 Pending인지 확인
        if(menteeApplication.getStatus() != ApplicationStatus.PENDING) throw new IllegalStateException("멘토링 신청 대기상태가 아닙니다.");
        if(mentorApplication.getStatus() != ApplicationStatus.PENDING) throw new IllegalStateException("멘토링 신청 대기상태가 아닙니다.");
        //6. 신청 과목이 같은지 확인
        if(mentorApplication.getSubject() != menteeApplication.getSubject()) throw new IllegalStateException("신청 과목이 서로 다릅니다.");
        //7. 멘티 기존 Active 매칭 확인
        Long menteeId = menteeApplication.getApplicant().getStudentId();
        List<MentoringProgram> menteeNowActiveMentoringList = mentoringProgramRepository.findByMenteeId(menteeId);
        if(!menteeNowActiveMentoringList.isEmpty()) throw new IllegalStateException("이미 진행 중인 멘토링이 있습니다.");
        //8. 현재 추천 결과에 선택 멘토가 포함되는지 확인
        List<MentorRecommendation> mentorRecommendationList = mentorRecommendationService.recommend(menteeId, menteeApplication.getSubject());

        MentoringProgram mentoringProgram = getMentoringProgram(mentorRecommendationList, mentorApplication, menteeApplication);
        mentoringProgramRepository.save(mentoringProgram);

        //10. 멘티 신청을 MATCHED로 변경
        menteeApplication.match();
        //11. 생성된 매칭 ID 리턴
        return mentoringProgram.getMentoringProgramId();
    }

    private static @NonNull MentoringProgram getMentoringProgram(List<MentorRecommendation> mentorRecommendationList, MentoringApplication mentorApplication, MentoringApplication menteeApplication) {
        MentorRecommendation selectedMentorRecommendation = null;

        for(MentorRecommendation mentorRecommendation : mentorRecommendationList){
            if(mentorRecommendation.mentorId().equals(mentorApplication.getApplicant().getStudentId())){
                selectedMentorRecommendation = mentorRecommendation;    // 추후 선택된 멘토에 대한 랭크 정보 등을 필요로 할때를 위해 남겨둠.
                break;
            }
        }
        if(selectedMentorRecommendation == null) throw new IllegalStateException("추천 멘토에 신청 멘토가 존재하지 않습니다.");

        //9. 멘토링프로그램 생성 및 저장
        MentoringProgram mentoringProgram = new MentoringProgram(
                mentorApplication.getApplicant(),
                menteeApplication.getApplicant(),
                menteeApplication.getSubject(),
                selectedMentorRecommendation.policyVersion()
                );
        return mentoringProgram;
    }
}
