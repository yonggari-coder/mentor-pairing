package toy_project.mentor_pairing.recommendation.policy;

import toy_project.mentor_pairing.domain.Subject;
import toy_project.mentor_pairing.recommendation.MentorRecommendation;
import toy_project.mentor_pairing.recommendation.RecommendationStudentData;

import java.util.List;

public interface MentorRecommendationPolicy {

    List<MentorRecommendation> recommend(
            RecommendationStudentData mentee,
            List<RecommendationStudentData> mentorCandidates,
            Subject subject
    );

    String version();
}
