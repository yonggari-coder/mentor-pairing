package toy_project.mentor_pairing.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy_project.mentor_pairing.domain.Student;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentRepository {
    private final EntityManager em;

    public void save(Student student){
        em.persist(student);
    }

    public Student findOne(Long id){
        return em.find(Student.class, id);
    }

    public List<Student> findAll(){
        return em.createQuery("select s from Student s", Student.class).getResultList();
    }
}
