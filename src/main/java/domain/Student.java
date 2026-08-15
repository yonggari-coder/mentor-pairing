package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter //추후 setter는 닫는 방향으로.
public class Student {

    @Id
    private Long studentId;
    private String email;
    private String name;
    private String major; //학과
    private int studyYear; //학년
}
