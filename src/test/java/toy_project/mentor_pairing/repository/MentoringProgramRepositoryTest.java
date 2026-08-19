package toy_project.mentor_pairing.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.MatchingStatus;
import toy_project.mentor_pairing.domain.MentoringProgram;
import toy_project.mentor_pairing.domain.Student;
import toy_project.mentor_pairing.domain.Subject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MentoringProgramRepositoryTest {

    @Autowired private MentoringProgramRepository mentoringProgramRepository;
    @Autowired private StudentRepository studentRepository;

    @Test
    public void 멘토링프로그램_매칭조회() throws Exception {
        //given
        Student mentor = new Student();
        Student mentee = new Student();

        mentor.setStudentId(10001L);
        mentor.setName("이용균");
        mentee.setStudentId(10002L);
        mentee.setName("양승원");

        studentRepository.save(mentor);
        studentRepository.save(mentee);

        MentoringProgram mentoringProgram = new MentoringProgram(mentor, mentee, Subject.MATH, "GoodPolicy");
        mentoringProgramRepository.save(mentoringProgram);
        //when
        List<MentoringProgram> mentoringList = mentoringProgramRepository.findByMenteeId(mentee.getStudentId());
        //then
        Assertions.assertThat(mentoringList.getFirst().getMentor()).isEqualTo(mentor);
        Assertions.assertThat(mentoringList.getFirst().getMentoringStatus()).isEqualTo(MatchingStatus.ACTIVE);
    }
}