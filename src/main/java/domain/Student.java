package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter //추후 setter는 닫는 방향으로.
public class Student {

    @Id
    private Long student_id;
    private String email;
    private String name;
}
