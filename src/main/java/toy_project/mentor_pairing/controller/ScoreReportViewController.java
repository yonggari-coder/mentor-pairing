package toy_project.mentor_pairing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toy_project.mentor_pairing.service.ScoreReportService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/score-reports")
public class ScoreReportViewController {
    private final ScoreReportService scoreReportService;

    @GetMapping("/new")
    public String createForm(
            @RequestParam Long studentId,
            Model model
    ){
        ScoreReportForm form = new ScoreReportForm();
        form.setStudentId(studentId);

        model.addAttribute("form", form);

        return "score-reports/createForm";
    }

    @PostMapping
    public String create(
            @ModelAttribute("form") ScoreReportForm form
    ){
        scoreReportService.registerScore(
                form.getStudentId(),
                form.getKoreanScore(),
                form.getEnglishScore(),
                form.getMathScore()
        );

        return "redirect:/students?scoreSuccess";
    }

}
