package toy_project.mentor_pairing.recommendation;

import toy_project.mentor_pairing.domain.Subject;

import java.time.LocalDate;

public record RecommendationStudentData(
        Long studentId,
        String name,
        Subject subject,
        int subjectScore,
        int totalScore,
        LocalDate birthDate
) {

}
