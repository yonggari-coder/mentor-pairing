package toy_project.mentor_pairing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toy_project.mentor_pairing.domain.MentoringApplication;
import toy_project.mentor_pairing.domain.MentoringRole;
import toy_project.mentor_pairing.domain.Subject;
import toy_project.mentor_pairing.service.MentoringApplicationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applications")
public class MentoringApplicationController {

    private final MentoringApplicationService applicationService;

    @GetMapping("/new")
    public String createForm(@RequestParam(required=false) Long studentId, Model model){
        ApplicationForm form = new ApplicationForm();
        form.setStudentId(studentId);

        model.addAttribute("form", form);
        model.addAttribute("subjects", Subject.values());
        model.addAttribute("roles", MentoringRole.values());

        return "applications/createForm";
    }

    @PostMapping
    public String apply(
            @ModelAttribute("form") ApplicationForm form
    ) {
        applicationService.applyMentoring(
                form.getStudentId(),
                form.getSubject(),
                form.getRole()
        );

        return "redirect:/applications?applicationSuccess";
    }

    @GetMapping
    public String list(Model model){
        List<MentoringApplication> applications = applicationService.findApplications();
        model.addAttribute("applications", applications);

        return "applications/list";
    }
}