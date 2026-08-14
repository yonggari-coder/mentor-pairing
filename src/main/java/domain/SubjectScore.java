package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class SubjectScore {
    @Id
    private Long scoreId;

    private Long reportId;
    private String subject;
    private int score;
}
