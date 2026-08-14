package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class MentoringApplication { //멘토링 신청 엔티티
    @Id
    private Long mentoringId;

    private Long studentId;
    private String subject;
    private String role;
    private String status;
    private Date createdAt;

}
