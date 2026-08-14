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
    private Long mentoring_id;

    private Long student_id;
    private String subject;
    private String role;
    private String status;
    private Date createdAt;

}
