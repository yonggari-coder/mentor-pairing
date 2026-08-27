package toy_project.mentor_pairing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.Student;
import toy_project.mentor_pairing.repository.ScoreReportRepository;
import toy_project.mentor_pairing.repository.StudentProfileRepository;
import toy_project.mentor_pairing.repository.StudentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final ScoreReportRepository scoreReportRepository;
    @Transactional
    public Long registerStudent(Student student){
        studentRepository.save(student);
        return student.getStudentId();
    }

    public List<Student> findStudents(){
        return studentRepository.findAll();
    }

    public boolean haveProfile(Long studentId){
        return studentProfileRepository.findByStudentId(studentId).isPresent();
    }

    public boolean haveScoreReport(Long studentId){
        return scoreReportRepository.findByStudentId(studentId).isPresent();
    }
}
