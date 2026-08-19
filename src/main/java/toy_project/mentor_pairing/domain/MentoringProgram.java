package toy_project.mentor_pairing.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MentoringProgram {
    @Id @GeneratedValue
    private Long mentoringProgramId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mentor_id", nullable=false)
    private Student mentor;

    @ManyToOne
    @JoinColumn(name="mentee_id")
    private Student mentee;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private MatchingStatus mentoringStatus;

    private String policyVersion;

    private LocalDateTime createdAt;
    private LocalDateTime endedAt;
}
