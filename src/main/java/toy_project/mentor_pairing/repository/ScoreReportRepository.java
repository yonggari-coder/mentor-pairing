package toy_project.mentor_pairing.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy_project.mentor_pairing.domain.ScoreReport;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScoreReportRepository {

    private final EntityManager em;

    public void save(ScoreReport scoreReport){
        em.persist(scoreReport);
    }

    public ScoreReport findById(Long id){
        ScoreReport scoreReport = em.find(ScoreReport.class, id);
        return scoreReport;
    }

    public Optional<ScoreReport> findByStudentId(Long studentId){ //JPQL 사용하기.
        return em.createQuery(
                        "select sr from ScoreReport sr where sr.student.studentId = :studentId",
                        ScoreReport.class
                )
                .setParameter("studentId", studentId)
                .getResultList().stream().findAny();
    }
}
