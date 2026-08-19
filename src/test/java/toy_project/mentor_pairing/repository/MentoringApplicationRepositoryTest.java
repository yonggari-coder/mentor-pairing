package toy_project.mentor_pairing.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.*;

import java.util.List;

@SpringBootTest
@Transactional
class MentoringApplicationRepositoryTest {

    @Autowired private EntityManager em;
    @Autowired private MentoringApplicationRepository mentoringApplicationRepository;
    @Autowired private StudentRepository studentRepository;

    @Test
    public void 멘토링신청등록조회_학생ID() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(10001L);
        student.setName("yong");
        studentRepository.save(student);

        MentoringApplication mentoringApplication = new MentoringApplication(student,Subject.MATH, MentoringRole.MENTOR);
        mentoringApplicationRepository.save(mentoringApplication);
        //when
        List<MentoringApplication> applicationList = mentoringApplicationRepository.findByStudentId(student.getStudentId());
        //then
        Assertions.assertThat(applicationList.getFirst().getRole()).isEqualTo(MentoringRole.MENTOR);
        Assertions.assertThat(applicationList.getFirst().getStatus()).isEqualTo(ApplicationStatus.PENDING);
    }

    @Test
    public void 멘토링신청등록조회_학생이름() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(10001L);
        student.setName("이용균");
        studentRepository.save(student);

        MentoringApplication mentoringApplication = new MentoringApplication(student, Subject.MATH, MentoringRole.MENTOR);
        mentoringApplicationRepository.save(mentoringApplication);
        //when
        List<MentoringApplication> applicationList = mentoringApplicationRepository.findByStudentName(student.getName());

        //then
        Assertions.assertThat(applicationList.getFirst().getApplicant().getName()).isEqualTo("이용균");
    }

    @Test
    public void 멘토링신청_취소() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(10001L);
        student.setName("이용균");
        studentRepository.save(student);

        MentoringApplication mentoringApplication = new MentoringApplication(student, Subject.MATH, MentoringRole.MENTOR);
        mentoringApplicationRepository.save(mentoringApplication);

        //when
        mentoringApplication.cancel();
        List<MentoringApplication> findMentoringApplication = mentoringApplicationRepository.findByStudentId(student.getStudentId());

        //then
        Assertions.assertThat(findMentoringApplication.getFirst().getStatus()).isEqualTo(ApplicationStatus.CANCELED);
    }

    @Test
    public void 멘토링_여러과목신청() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(10001L);
        student.setName("이용균");
        studentRepository.save(student);

        MentoringApplication mentoringApplication1 = new MentoringApplication(student, Subject.KOREAN, MentoringRole.MENTEE);
        MentoringApplication mentoringApplication2 = new MentoringApplication(student, Subject.MATH, MentoringRole.MENTOR);
        mentoringApplicationRepository.save(mentoringApplication1);
        mentoringApplicationRepository.save(mentoringApplication2);

        //when
        List<MentoringApplication> applicationList = mentoringApplicationRepository.findByStudentName(student.getName());
        //then
        Assertions.assertThat(applicationList.size()).isEqualTo(2);
    }
}

