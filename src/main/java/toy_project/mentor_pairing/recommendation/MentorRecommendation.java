package toy_project.mentor_pairing.recommendation;

import toy_project.mentor_pairing.domain.Subject;

import java.time.LocalDate;

public record MentorRecommendation(
        Long mentorId,
        String mentorName,
        Subject subject,
        int mentorSubjectScore,
        int subjectScoreDifference,
        int totalScoreDifference,
        int rank,
        String policyVersion
) {
}
