package domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ScoreReport {

    @Id
    private Long reportId;

    @OneToOne(fetch= FetchType.LAZY)
    private Student student;

    @Embedded
    @AttributeOverride(name="score", column=@Column(name="korean_score"))
    private SubjectScore korean;

    @Embedded
    @AttributeOverride(name="score", column=@Column(name="english_score"))
    private SubjectScore english;

    @Embedded
    @AttributeOverride(name="score", column=@Column(name="math_score"))
    private SubjectScore math;

    public int sumScore(){
        int sum = 0;
        sum+=korean.getScore();
        sum+=english.getScore();
        sum+=math.getScore();
        return sum;
    }
}
