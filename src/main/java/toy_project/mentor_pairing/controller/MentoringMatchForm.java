package toy_project.mentor_pairing.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentoringMatchForm {
    private Long menteeApplicationId;
    private Long mentorApplicationId;
}
