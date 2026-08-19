package toy_project.mentor_pairing.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy_project.mentor_pairing.domain.StudentProfile;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentProfileRepository {

    private final EntityManager em;

    public void save(StudentProfile studentProfile){
        em.persist(studentProfile);
    }

    public Optional<StudentProfile> findByStudentId(Long studentId){
        return em.createQuery("select sp from StudentProfile sp where sp.student.studentId = :studentId", StudentProfile.class)
                .setParameter("studentId", studentId)
                .getResultList().stream().findFirst();
    }
}
