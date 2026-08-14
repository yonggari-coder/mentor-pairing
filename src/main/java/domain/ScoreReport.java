package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ScoreReport {
    @Id
    private Long reportId;

    private Long studentId;
    private int year;
    private int semester;
}
