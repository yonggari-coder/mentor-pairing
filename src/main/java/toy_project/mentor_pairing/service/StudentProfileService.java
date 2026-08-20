package toy_project.mentor_pairing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.Student;
import toy_project.mentor_pairing.domain.StudentProfile;
import toy_project.mentor_pairing.repository.StudentProfileRepository;
import toy_project.mentor_pairing.repository.StudentRepository;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentProfileService {
    private final StudentRepository studentRepository;
    private final StudentProfileRepository studentProfileRepository;

    @Transactional
    public Long registerProfile(Long studentId, LocalDate birthDate) {
        Student student = studentRepository.findOne(studentId);
        if (student == null) throw new IllegalArgumentException("존재하지 않는 학생입니다.");

        StudentProfile profile = new StudentProfile(student, birthDate);
        studentProfileRepository.save(profile);

        return profile.getProfileId();
    }
}
