package toy_project.mentor_pairing.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.*;
import toy_project.mentor_pairing.repository.MentoringApplicationRepository;
import toy_project.mentor_pairing.repository.StudentRepository;
import toy_project.mentor_pairing.exception.MentoringException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MentoringApplicationServiceTest {

    @Autowired private MentoringApplicationService mentoringApplicationService;

    @Autowired private MentoringApplicationRepository mentoringApplicationRepository;
    @Autowired private StudentRepository studentRepository;

    @Test
    public void 멘토링_정상신청() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(10001L);
        student.setName("이용균");
        studentRepository.save(student);

        mentoringApplicationService.applyMentoring(student.getStudentId(), Subject.MATH, MentoringRole.MENTEE);
        //when
        List<MentoringApplication> applicationList = mentoringApplicationService.searchApplication(student.getStudentId());
        MentoringApplication findMentoringApplication = applicationList.getFirst();

        //then
        Assertions.assertThat(findMentoringApplication.getSubject()).isEqualTo(Subject.MATH);
        Assertions.assertThat(findMentoringApplication.getRole()).isEqualTo(MentoringRole.MENTEE);
    }

    @Test
    public void 존재하지않는_학생() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(10001L);
        student.setName("이용균");
        studentRepository.save(student);

        //when //then
        assertThrows(MentoringException.class, () ->{
            mentoringApplicationService.applyMentoring(10002L, Subject.MATH, MentoringRole.MENTOR);
        });
    }

    @Test
    public void 본인_신청_취소() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(10001L);
        student.setName("이용균");
        studentRepository.save(student);
        //when
        mentoringApplicationService.applyMentoring(student.getStudentId(), Subject.KOREAN, MentoringRole.MENTEE);
        List<MentoringApplication> applicationList = mentoringApplicationRepository.findByStudentId(student.getStudentId());
        MentoringApplication findMentoringApplication = applicationList.getFirst();
        mentoringApplicationService.cancelMentoring(student.getStudentId(), findMentoringApplication.getApplicationId());

        //then
        Assertions.assertThat(findMentoringApplication.getStatus()).isEqualTo(ApplicationStatus.CANCELED);
    }

    @Test
    public void 타인_신청_취소() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(10001L);
        student.setName("이용균");
        studentRepository.save(student);
        //when
        mentoringApplicationService.applyMentoring(student.getStudentId(), Subject.ENGLISH, MentoringRole.MENTEE);
        List<MentoringApplication> applicationList = mentoringApplicationRepository.findByStudentId(student.getStudentId());
        MentoringApplication findMentoringApplication = applicationList.getFirst();

        //then
        assertThrows(MentoringException.class, ()->{
            mentoringApplicationService.cancelMentoring(10002L, findMentoringApplication.getApplicationId());
        });

    }
}