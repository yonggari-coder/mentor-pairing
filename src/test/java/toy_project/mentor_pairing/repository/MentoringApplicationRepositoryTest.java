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

    @Test
    public void 멘토링_멘토추천리스트() throws Exception {
        //given
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Student student4 = new Student();
        Student student5 = new Student();

        student1.setStudentId(10001L);
        student1.setName("Lee");

        student2.setStudentId(10002L);
        student2.setName("Yong");

        student3.setStudentId(10003L);
        student3.setName("Gyun");

        student4.setStudentId(10004L);
        student4.setName("Kim");

        student5.setStudentId(10005L);
        student5.setName("Min");

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        studentRepository.save(student5);

        MentoringApplication mentoringApplication1 = new MentoringApplication(student1, Subject.MATH, MentoringRole.MENTOR);
        MentoringApplication mentoringApplication2 = new MentoringApplication(student2, Subject.ENGLISH, MentoringRole.MENTOR);
        MentoringApplication mentoringApplication3 = new MentoringApplication(student3, Subject.MATH, MentoringRole.MENTEE);
        MentoringApplication mentoringApplication4 = new MentoringApplication(student4, Subject.MATH, MentoringRole.MENTOR);
        mentoringApplication4.cancel();
        MentoringApplication mentoringApplication5 = new MentoringApplication(student5, Subject.MATH, MentoringRole.MENTOR);

        mentoringApplicationRepository.save(mentoringApplication1);
        mentoringApplicationRepository.save(mentoringApplication2);
        mentoringApplicationRepository.save(mentoringApplication3);
        mentoringApplicationRepository.save(mentoringApplication4);
        mentoringApplicationRepository.save(mentoringApplication5);
        //when
        List<MentoringApplication> mentorCandidates = mentoringApplicationRepository.findMentorCandidates(Subject.MATH, student5.getStudentId());

        //then
        Assertions.assertThat(mentorCandidates.size()).isEqualTo(1);
        Assertions.assertThat(mentorCandidates.getFirst().getApplicant().getName()).isEqualTo("Lee");
    }
}

