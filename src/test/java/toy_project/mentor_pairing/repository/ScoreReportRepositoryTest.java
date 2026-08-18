package toy_project.mentor_pairing.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.ScoreReport;
import toy_project.mentor_pairing.domain.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScoreReportRepositoryTest {
    @Autowired
    private ScoreReportRepository scoreReportRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void 성적표등록조회() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(10001L);
        ScoreReport scoreReport = new ScoreReport(student, 100, 90, 85);
        //when
        scoreReportRepository.save(scoreReport);
        Long reportId = scoreReport.getReportId();
        //then
        ScoreReport findReport = scoreReportRepository.findById(scoreReport.getReportId());
        Assertions.assertThat(findReport.getReportId()).isEqualTo(reportId);
    }

    @Test
    public void 특정학생_성적조회() throws Exception {
        //given
        Student student = new Student();
        student.setName("이용균");
        student.setStudentId(10001L);
        studentRepository.save(student);

        ScoreReport scoreReport = new ScoreReport(student,80, 90, 100);
        scoreReportRepository.save(scoreReport);

        //when
        Long studentId = student.getStudentId();
        Long reportId = scoreReport.getReportId();
        ScoreReport findReport = scoreReportRepository.findByStudentId(studentId);

        //then
        Assertions.assertThat(reportId).isEqualTo(findReport.getReportId());
    }
}