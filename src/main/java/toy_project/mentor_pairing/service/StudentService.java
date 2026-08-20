package toy_project.mentor_pairing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.Student;
import toy_project.mentor_pairing.repository.StudentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Long registerStudent(Student student){
        studentRepository.save(student);
        return student.getStudentId();
    }
}
