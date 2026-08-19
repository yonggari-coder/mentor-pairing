package toy_project.mentor_pairing.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.Student;
import toy_project.mentor_pairing.domain.StudentProfile;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Transactional
public class StudentProfileRepositoryTest {

    @Autowired private StudentProfileRepository studentProfileRepository;
    @Autowired private StudentRepository studentRepository;

    @Test
    public void 학생프로필_정상등록() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(10001L);
        student.setName("Yong");
        studentRepository.save(student);

        StudentProfile studentProfile = new StudentProfile(student, LocalDate.of(2001,2,3));
        studentProfileRepository.save(studentProfile);
        //when
        Optional<StudentProfile> findStudentProfile = studentProfileRepository.findByStudentId(student.getStudentId());

        //then
        Assertions.assertThat(findStudentProfile.get().getBirthDate()).isEqualTo(LocalDate.of(2001,2,3));
    }

    @Test
    public void 학생프로필_프로필없는_학생조회() throws Exception {
        //given //when
        Optional<StudentProfile> findStudentProfile = studentProfileRepository.findByStudentId(10001L);

        //then
        Assertions.assertThat(findStudentProfile).isEmpty();
    }

}
