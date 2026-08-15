package toy_project.mentor_pairing.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class SubjectScore {

    private int score;

    protected SubjectScore (){} //jpa 표준을 위해 기본 생성하자!!

    protected SubjectScore(int score){
        this.score = score;
    }

}
