package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class StudentProfile {
    @Id
    private String studentId;
    private Date birthDate;
}
