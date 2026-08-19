package toy_project.mentor_pairing.recommendation.policy;

import org.springframework.stereotype.Component;
import toy_project.mentor_pairing.domain.Subject;
import toy_project.mentor_pairing.recommendation.MentorRecommendation;
import toy_project.mentor_pairing.recommendation.RecommendationStudentData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ScoreBasedRecommendationPolicy implements MentorRecommendationPolicy{

    @Override
    public List<MentorRecommendation> recommend(
            RecommendationStudentData mentee,
            List<RecommendationStudentData> mentorCandidates,
            Subject subject
    ) {
        List<RecommendationStudentData> sortedCandidates =
                mentorCandidates.stream()
                        .filter(candidate ->
                                isEligibleCandidate(candidate, mentee)
                        )
                        .sorted(recommendationComparator(mentee))
                        .toList();

        List<MentorRecommendation> recommendations =
                new ArrayList<>(sortedCandidates.size());

        for (int index = 0; index < sortedCandidates.size(); index++) {
            RecommendationStudentData candidate =
                    sortedCandidates.get(index);

            recommendations.add(
                    createRecommendation(
                            candidate,
                            mentee,
                            subject,
                            index + 1
                    )
            );
        }

        return recommendations;
    }

    @Override
    public String version() {
        return "SCORE_BASED_RECOMMENDATION";
    }

    private boolean isEligibleCandidate(
            RecommendationStudentData candidate,
            RecommendationStudentData mentee
    ) {
        return candidate.subjectScore() > mentee.subjectScore();
    }

    private Comparator<RecommendationStudentData> recommendationComparator(
            RecommendationStudentData mentee
    ) {
        return Comparator
                .comparingInt(
                        (RecommendationStudentData candidate) ->
                                calculateSubjectScoreDifference(
                                        candidate,
                                        mentee
                                )
                )
                .thenComparingInt(candidate ->
                        calculateTotalScoreDifference(candidate, mentee)
                )
                .thenComparing(
                        RecommendationStudentData::birthDate
                );
    }

    private int calculateSubjectScoreDifference(
            RecommendationStudentData candidate,
            RecommendationStudentData mentee
    ) {
        return candidate.subjectScore() - mentee.subjectScore();
    }

    private int calculateTotalScoreDifference(
            RecommendationStudentData candidate,
            RecommendationStudentData mentee
    ) {
        return Math.abs(
                candidate.totalScore() - mentee.totalScore()
        );
    }

    private MentorRecommendation createRecommendation(
            RecommendationStudentData candidate,
            RecommendationStudentData mentee,
            Subject subject,
            int rank
    ) {
        return new MentorRecommendation(
                candidate.studentId(),
                candidate.name(),
                subject,
                candidate.subjectScore(),
                calculateSubjectScoreDifference(candidate, mentee),
                calculateTotalScoreDifference(candidate, mentee),
                rank,
                version()
        );
    }
}
