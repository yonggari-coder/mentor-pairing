package toy_project.mentor_pairing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.Student;
import toy_project.mentor_pairing.repository.StudentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    @Transactional
    public Long registerStudent(Student student){
        studentRepository.save(student);
        return student.getStudentId();
    }

    public List<Student> findStudents(){
        return studentRepository.findAll();
    }
}
