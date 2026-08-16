package toy_project.mentor_pairing.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy_project.mentor_pairing.domain.Student;

@Repository
@RequiredArgsConstructor
public class StudentRepository {
    private final EntityManager em;

    public void save(Student student){
        em.persist(student);
    }
}
