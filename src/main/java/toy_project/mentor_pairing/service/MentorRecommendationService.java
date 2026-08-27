package toy_project.mentor_pairing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.*;
import toy_project.mentor_pairing.recommendation.MentorRecommendation;
import toy_project.mentor_pairing.recommendation.RecommendationStudentData;
import toy_project.mentor_pairing.recommendation.policy.MentorRecommendationPolicy;
import toy_project.mentor_pairing.repository.MentoringApplicationRepository;
import toy_project.mentor_pairing.repository.ScoreReportRepository;
import toy_project.mentor_pairing.repository.StudentProfileRepository;
import toy_project.mentor_pairing.repository.StudentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

// TODO: 학생/성적표/프로필 없을 경우에 대한 예외 처리 추가

public class MentorRecommendationService {

    private final StudentRepository studentRepository;
    private final ScoreReportRepository scoreReportRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final MentoringApplicationRepository mentoringApplicationRepository;
    private final MentorRecommendationPolicy recommendationPolicy;

    public List<MentorRecommendation> recommend(
            Long menteeId,
            Subject subject
    ){
        //1. 멘티 조회
        Student student = studentRepository.findOne(menteeId);

        //2. 멘티 추천 데이터 생성
        RecommendationStudentData menteeStudentData = createStudentData(student, subject);

        //3. 멘토 후보 신청 조회 //4. 후보 추천 데이터 생성
        List<RecommendationStudentData> recommendationStudentDataList = new ArrayList<>();
        List<MentoringApplication> mentorList = mentoringApplicationRepository.findMentorCandidates(subject, menteeId);

        for(MentoringApplication application : mentorList){
            RecommendationStudentData mentorData = createStudentData(application.getApplicant(), subject);
            recommendationStudentDataList.add(mentorData);
        }
        //5. 정책 실행
        List<MentorRecommendation> mentorRecommendationList = recommendationPolicy.recommend(
                menteeStudentData,
                recommendationStudentDataList,
                subject
        );

        //6. 결과 반환
        return mentorRecommendationList;
    }

    private RecommendationStudentData createStudentData(
            Student student,
            Subject subject
    ){

        ScoreReport scoreReport = scoreReportRepository.findByStudentId(student.getStudentId()).get();
        StudentProfile studentProfile = studentProfileRepository.findByStudentId(student.getStudentId()).get();

        int koreanScore = 0;
        int mathScore = 0;
        int englishScore = 0;
        koreanScore = scoreReport.getKorean().getScore();
        mathScore = scoreReport.getMath().getScore();
        englishScore = scoreReport.getEnglish().getScore();
        int totalScore = koreanScore+mathScore+englishScore;

        int subjectScore = 0;
        if(subject == Subject.MATH) subjectScore=mathScore;
        else if(subject == Subject.ENGLISH) subjectScore=englishScore;
        else if(subject == Subject.KOREAN) subjectScore = koreanScore;

        LocalDate birthDate = studentProfile.getBirthDate();

        RecommendationStudentData recommendationStudentData = new RecommendationStudentData(
                student.getStudentId(),
                student.getName(),
                subject,
                subjectScore,
                totalScore,
                birthDate
        );

        return recommendationStudentData;
    }
}
