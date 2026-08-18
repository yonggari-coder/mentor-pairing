package toy_project.mentor_pairing.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy_project.mentor_pairing.domain.MentoringApplication;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MentoringApplicationRepository {

    private final EntityManager em;

    public void save(MentoringApplication mentoringApplication){ //멘토링 신청
        em.persist(mentoringApplication);
    }

    public List<MentoringApplication> findByStudentName(String name){
        return em.createQuery("select ma from MentoringApplication ma where ma.applicant.name = :name",
                        MentoringApplication.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<MentoringApplication> findByStudentId(Long id) {
        return em.createQuery("select ma from MentoringApplication ma where ma.applicant.studentId = :studentId",
                        MentoringApplication.class)
                .setParameter("studentId", id)
                .getResultList();

    }

    public MentoringApplication findByApplicationId(Long id){
        return em.find(MentoringApplication.class, id);
    }
}
