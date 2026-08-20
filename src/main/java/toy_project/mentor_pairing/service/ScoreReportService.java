package toy_project.mentor_pairing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.ScoreReport;
import toy_project.mentor_pairing.domain.Student;
import toy_project.mentor_pairing.repository.ScoreReportRepository;
import toy_project.mentor_pairing.repository.StudentRepository;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor

//todo: 점수 입력 검증 진행하기

public class ScoreReportService {
    private final StudentRepository studentRepository;
    private final ScoreReportRepository scoreReportRepository;

    @Transactional
    public void registerScore(
            Long studentId,
            int koreanScore,
            int englishScore,
            int mathScore
    ){
        Student student = studentRepository.findOne(studentId);
        if(student==null) throw new IllegalArgumentException("존재하지 않는 학생입니다.");

       ScoreReport scoreReport = new ScoreReport(
               student,
               koreanScore,
               englishScore,
               mathScore
       );

       scoreReportRepository.save(scoreReport);
    }

}
