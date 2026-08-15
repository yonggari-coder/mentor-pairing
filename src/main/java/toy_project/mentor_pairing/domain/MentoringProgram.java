package toy_project.mentor_pairing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class MentoringProgram {
    @Id
    private Long mentoringProgramId;

    private Long mentorId;
    private Long menteeId;

    private String subject;
    private String status;

    private String policyVersion;
    private Date createdAt;
    private Date endedAt;
}
