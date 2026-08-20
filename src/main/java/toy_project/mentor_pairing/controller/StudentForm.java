package toy_project.mentor_pairing.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class StudentForm {
    private Long studentId;
    private String name;
}
