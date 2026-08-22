package toy_project.mentor_pairing.domain;

import jakarta.persistence.*;
import lombok.Getter;
import toy_project.mentor_pairing.exception.MentoringException;

import java.time.LocalDateTime;

@Entity
@Getter

//TODO : 생성자 NULL 검증 필요
public class MentoringProgram {
    @Id @GeneratedValue
    private Long mentoringProgramId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mentor_id", nullable=false)
    private Student mentor;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mentee_id", nullable=false)
    private Student mentee;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private MatchingStatus mentoringStatus;

    private String policyVersion;

    private LocalDateTime createdAt;
    private LocalDateTime endedAt;

    protected MentoringProgram(){}

    public MentoringProgram(Student mentor, Student mentee, Subject subject, String policyVersion){

        if(mentor.getStudentId().equals(mentee.getStudentId())) throw new MentoringException("자기 자신과 멘토링을 진행할 수 없습니다.");

        this.mentor = mentor;
        this.mentee = mentee;
        this.subject = subject;
        this.mentoringStatus = MatchingStatus.ACTIVE;
        this.policyVersion= policyVersion;
        this.createdAt = LocalDateTime.now();
        this.endedAt = null;
    }

    public void end(){
        if(mentoringStatus != MatchingStatus.ACTIVE) {
            throw new MentoringException("이미 종료된 멘토링입니다.");
        }

        this.endedAt = LocalDateTime.now();
        this.mentoringStatus = MatchingStatus.ENDED;
    }
}
