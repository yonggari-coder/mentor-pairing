package toy_project.mentor_pairing.domain;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDate;

@Entity
@Getter
public class StudentProfile {

    @Id @GeneratedValue
    private Long profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id",unique=true, nullable=false)
    private Student student;

    protected StudentProfile(){}

    public StudentProfile(Student student, LocalDate birthDate){
        this.student = student;
        this.birthDate = birthDate;
    }

    private LocalDate birthDate;

    public void changeBirthDate(LocalDate birthDate){
        this.birthDate = birthDate;
    }
}
