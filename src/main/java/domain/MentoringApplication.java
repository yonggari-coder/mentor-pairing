package domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class MentoringApplication { //멘토링 신청 엔티티

    @Id
    private Long applicationId; //멘토링 신청 식별자

    @ManyToOne(fetch=FetchType.LAZY)
    private Student applicant; //신청자

    private String subject; //신청 과목

    @Enumerated(EnumType.STRING)
    private MentoringRole role;    //신청 역할

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;  //현재 승인 상태

    private Date createdAt; //기타 정보 - 생성일시.

}
