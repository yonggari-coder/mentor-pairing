package toy_project.mentor_pairing.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ScoreReport {

    @Id @GeneratedValue
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

    protected ScoreReport(){}

    public ScoreReport(int korScore, int engScore, int mathScore){
        this.korean = new SubjectScore(korScore);
        this.english = new SubjectScore(engScore);
        this.math = new SubjectScore(mathScore);
    }

    public int sumScore(){
        int sum = 0;
        sum+=korean.getScore();
        sum+=english.getScore();
        sum+=math.getScore();
        return sum;
    }
}
