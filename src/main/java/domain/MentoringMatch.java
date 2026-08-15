package domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MentoringMatch {

    @Id @GeneratedValue
    private Long matchId;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mentor_application_id")
    private MentoringApplication mentorApplication;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mentee_application_id")
    private MentoringApplication menteeApplication;

}