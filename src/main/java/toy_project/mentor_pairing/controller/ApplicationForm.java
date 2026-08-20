package toy_project.mentor_pairing.controller;

import lombok.Getter;
import lombok.Setter;
import toy_project.mentor_pairing.domain.MentoringRole;
import toy_project.mentor_pairing.domain.Subject;

@Getter
@Setter
public class ApplicationForm {
    private Long studentId;
    private Subject subject;
    private MentoringRole role;
}
