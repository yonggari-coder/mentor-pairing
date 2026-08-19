package toy_project.mentor_pairing.recommendation.policy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import toy_project.mentor_pairing.domain.Subject;
import toy_project.mentor_pairing.recommendation.MentorRecommendation;
import toy_project.mentor_pairing.recommendation.RecommendationStudentData;
import java.time.LocalDate;
import java.util.List;

class ScoreBasedRecommendationPolicyTest {
    private final MentorRecommendationPolicy policy =
            new ScoreBasedRecommendationPolicy();

    @Test
    public void 과목점수정책테스트() throws Exception {
        //given
        RecommendationStudentData mentee = new RecommendationStudentData(
                10001L, "이용균",  Subject.MATH, 80,280, LocalDate.of(2002,1,1)
        );
        RecommendationStudentData studentData1 = new RecommendationStudentData(
                10002L, "김재민", Subject.MATH, 65, 285, LocalDate.of(2002,3,2)
        );
        RecommendationStudentData studentData2 = new RecommendationStudentData(
                10003L, "양승원", Subject.MATH, 85, 290, LocalDate.of(2003,2,6)
        );
        RecommendationStudentData studentData3 = new RecommendationStudentData(
                10004L, "진도준", Subject.MATH, 90, 283, LocalDate.of(2004,11,11)
        );

        List<RecommendationStudentData> mentorCandidates = List.of(studentData1, studentData2, studentData3);

        //when
        List<MentorRecommendation> recommendations = policy.recommend(
                mentee,
                mentorCandidates,
                Subject.MATH
        );
        //then
        Assertions.assertThat(recommendations).hasSize(2);
        Assertions.assertThat(recommendations)
                .extracting(MentorRecommendation::mentorId)
                .containsExactly(10003L, 10004L);
        Assertions.assertThat(recommendations.getFirst().rank())
                .isEqualTo(1);

    }
}