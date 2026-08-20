package toy_project.mentor_pairing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toy_project.mentor_pairing.domain.MentoringApplication;
import toy_project.mentor_pairing.domain.MentoringRole;
import toy_project.mentor_pairing.service.MentoringApplicationService;
import toy_project.mentor_pairing.service.MentoringProgramService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/programs")
public class MentoringProgramViewController {

    private final MentoringApplicationService mentoringApplicationService;
    private final MentoringProgramService mentoringProgramService;

    @GetMapping("/match")
    public String matchForm(
            @RequestParam(required = false)
            Long menteeApplicationId,
            Model model
    ) {
        model.addAttribute(
                "mentees",
                mentoringApplicationService.findWaitingApplications(MentoringRole.MENTEE)
        );

        model.addAttribute(
                "form",
                new MentoringMatchForm()
        );

        if (menteeApplicationId != null) {
            MentoringApplication menteeApplication =
                    mentoringApplicationService.findMenteeApplication(menteeApplicationId);

            model.addAttribute(
                    "selectedMentee",
                    menteeApplication
            );

            model.addAttribute(
                    "mentors",
                    mentoringApplicationService.findMentorCandidates(
                            menteeApplication.getSubject(),
                            menteeApplication.getApplicant().getStudentId()
                    )
            );
        }

        return "programs/matchForm";
    }



    @PostMapping("/match")
    public String match(@ModelAttribute("form") MentoringMatchForm form){
        mentoringProgramService.match(
                form.getMenteeApplicationId(),
                form.getMentorApplicationId()
        );

        return "redirect:/programs?matchSuccess";
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute(
                "programs",
                mentoringProgramService.findPrograms()
        );

        return "programs/list";
    }
}
