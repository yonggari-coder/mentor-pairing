package toy_project.mentor_pairing.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.*;
import toy_project.mentor_pairing.recommendation.MentorRecommendation;
import toy_project.mentor_pairing.repository.ScoreReportRepository;
import toy_project.mentor_pairing.repository.StudentProfileRepository;
import toy_project.mentor_pairing.repository.StudentRepository;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class MentorRecommendationServiceTest {
    @Autowired private StudentRepository studentRepository;
    @Autowired private ScoreReportRepository scoreReportRepository;
    @Autowired private StudentProfileRepository studentProfileRepository;

    @Autowired private MentorRecommendationService recommendationService;
    @Autowired private MentoringApplicationService mentoringApplicationService;

    @Test
    public void 멘토추천리스트_조회() throws Exception {
        //given
        // 1. 멘티 정보
        Student mentee = new Student();
        mentee.setStudentId(10001L);
        mentee.setName("이용균");
        studentRepository.save(mentee);
        ScoreReport scoreReport = new ScoreReport(mentee, 80, 80, 70);
        scoreReportRepository.save(scoreReport);
        StudentProfile studentProfile = new StudentProfile(mentee, LocalDate.of(2002, 5,5));
        studentProfileRepository.save(studentProfile);
        mentoringApplicationService.applyMentoring(mentee.getStudentId(), Subject.MATH, MentoringRole.MENTEE);

        // 2. 멘토 후보
        Student mentorApplicant1 = new Student();
        mentorApplicant1.setStudentId(10002L);
        mentorApplicant1.setName("양승원");
        studentRepository.save(mentorApplicant1);
        ScoreReport mentorScoreReport1 = new ScoreReport(mentorApplicant1, 80, 80, 90);
        scoreReportRepository.save(mentorScoreReport1);
        StudentProfile mentorApplicantProfile1 = new StudentProfile(mentorApplicant1, LocalDate.of(2001,4,12));
        studentProfileRepository.save(mentorApplicantProfile1);
        mentoringApplicationService.applyMentoring(mentorApplicant1.getStudentId(), Subject.MATH, MentoringRole.MENTOR);

        Student mentorApplicant2 = new Student();
        mentorApplicant2.setStudentId(10003L);
        mentorApplicant2.setName("김재민");
        studentRepository.save(mentorApplicant2);
        ScoreReport mentorScoreReport2 = new ScoreReport(mentorApplicant2, 80, 80, 92);
        scoreReportRepository.save(mentorScoreReport2);
        StudentProfile mentorApplicantProfile2 = new StudentProfile(mentorApplicant2, LocalDate.of(2004,8,23));
        studentProfileRepository.save(mentorApplicantProfile2);
        mentoringApplicationService.applyMentoring(mentorApplicant2.getStudentId(), Subject.MATH, MentoringRole.MENTOR);
        //when
        List<MentorRecommendation> mentorRecommendationList = recommendationService.recommend(mentee.getStudentId(), Subject.MATH);
        MentorRecommendation bestMentor = mentorRecommendationList.getFirst();
        //then
        Assertions.assertThat(bestMentor.mentorName()).isEqualTo("양승원");
        Assertions.assertThat(mentorRecommendationList).hasSize(2);
        Assertions.assertThat(mentorRecommendationList).extracting(MentorRecommendation::mentorName).containsExactly("양승원","김재민");
    }
}