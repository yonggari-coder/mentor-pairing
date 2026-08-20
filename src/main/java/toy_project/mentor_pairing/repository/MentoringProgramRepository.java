package toy_project.mentor_pairing.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy_project.mentor_pairing.domain.MatchingStatus;
import toy_project.mentor_pairing.domain.MentoringProgram;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MentoringProgramRepository {

    private final EntityManager em;

    public void save(MentoringProgram mentoringProgram){
        em.persist(mentoringProgram);
    }

    public MentoringProgram findByMatchingId(Long matchId){
        return em.find(MentoringProgram.class, matchId);
    }

    public List<MentoringProgram> findByMenteeId(Long menteeId){    //활성화되어있는 경우에 대해서만 일단 조회
        return em.createQuery("select mp from MentoringProgram mp where mp.mentee.studentId = :menteeId"+
                        " and mp.mentoringStatus = :mentoringStatus", MentoringProgram.class)
                        .setParameter("menteeId", menteeId)
                        .setParameter("mentoringStatus", MatchingStatus.ACTIVE)
                        .getResultList();
    }

    public List<MentoringProgram> findAll(){
        return em.createQuery("select mp from MentoringProgram mp", MentoringProgram.class)
                .getResultList();
    }

}
