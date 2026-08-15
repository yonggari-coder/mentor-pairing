package toy_project.mentor_pairing;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.ScoreReport;
import toy_project.mentor_pairing.domain.Student;
import toy_project.mentor_pairing.domain.SubjectScore;

@SpringBootTest
@Transactional
public class DomainTest {

    @Autowired
    private EntityManager em;

    @Test
    public void 학생등록() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(12345L);
        student.setName("Yonggyun");
        em.persist(student);

        //when
        Student student1 = em.find(Student.class, student.getStudentId());
        //then
        Assertions.assertThat(student1.getName()).isEqualTo("Yonggyun");
    }

    @Test
    public void 성적점수_합계산() throws Exception {
        //given
        ScoreReport report = new ScoreReport(90, 85, 100);
        //when
        int reportTotalScore = report.sumScore();

        //then
        Assertions.assertThat(report.sumScore()).isEqualTo(90+85+100);
    }
}
