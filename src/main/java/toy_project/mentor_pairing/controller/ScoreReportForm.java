package toy_project.mentor_pairing.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreReportForm {
    private Long studentId;

    private Integer koreanScore;
    private Integer englishScore;
    private Integer mathScore;
}
