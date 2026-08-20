package toy_project.mentor_pairing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.MentoringApplication;
import toy_project.mentor_pairing.domain.MentoringRole;
import toy_project.mentor_pairing.domain.Student;
import toy_project.mentor_pairing.domain.Subject;
import toy_project.mentor_pairing.repository.MentoringApplicationRepository;
import toy_project.mentor_pairing.repository.StudentRepository;

import java.util.List;


@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MentoringApplicationService {

    private final MentoringApplicationRepository mentoringApplicationRepository;
    private final StudentRepository studentRepository;

    public List<MentoringApplication> searchApplication(Long studentId){
        return mentoringApplicationRepository.findByStudentId(studentId);
    }

    @Transactional
    public Long applyMentoring(Long studentId, Subject subject, MentoringRole role){
        //엔티티 조회
        Student student = studentRepository.findOne(studentId);

        if(student == null){
            throw new IllegalArgumentException("존재하지 않는 학생입니다.");
        }

        MentoringApplication mentoringApplication = new MentoringApplication(student, subject, role);
        mentoringApplicationRepository.save(mentoringApplication);

        return mentoringApplication.getApplicationId();
    }

    @Transactional
    public void cancelMentoring(Long studentId, Long applicationId){
        MentoringApplication mentoringApplication = mentoringApplicationRepository.findByApplicationId(applicationId);

        if(mentoringApplication != null){
            if(mentoringApplication.getApplicant().getStudentId().equals(studentId)){
                mentoringApplication.cancel();
            } else{
                throw new IllegalStateException("권한이 없는 취소 요청입니다.");
            }

        } else{
            throw new IllegalStateException("신청 ID와 일치하는 멘토링 신청이 없습니다.");
        }
    }

    public List<MentoringApplication> findApplications(){
        return mentoringApplicationRepository.findAll();
    }
}
