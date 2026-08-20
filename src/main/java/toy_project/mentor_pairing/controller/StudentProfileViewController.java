package toy_project.mentor_pairing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toy_project.mentor_pairing.service.StudentProfileService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student-profiles")
public class StudentProfileViewController {
    private final StudentProfileService studentProfileService;

    @GetMapping("/new")
    public String createForm(@RequestParam Long studentId, Model model){
        StudentProfileForm form = new StudentProfileForm();

        form.setStudentId(studentId);
        model.addAttribute("form", form);

        return "student-profiles/createForm";
    }

    @PostMapping
    public String create(@ModelAttribute("form") StudentProfileForm form){
        studentProfileService.registerProfile(form.getStudentId(), form.getBirthDate());

        return "redirect:/students?profileSuccess";
    }


}
