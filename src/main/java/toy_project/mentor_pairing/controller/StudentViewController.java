package toy_project.mentor_pairing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import toy_project.mentor_pairing.domain.Student;
import toy_project.mentor_pairing.service.StudentService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentViewController {
    private final StudentService studentService;

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("form", new StudentForm());
        return "students/createForm";
    }

    @PostMapping
    public String create(@ModelAttribute("form") StudentForm form){
        Student student = new Student();
        student.setStudentId(form.getStudentId());
        student.setName(form.getName());

        studentService.registerStudent(student);

        return "redirect:/students";
    }

    @GetMapping
    public String studentList(Model model){
        List<Student> students = studentService.findStudents();

        Map<Long, Boolean> profileStatus = students.stream()
                        .collect(Collectors.toMap(Student::getStudentId,
                                student -> studentService.haveProfile(student.getStudentId()) ));

        Map<Long, Boolean> scoreStatus = students.stream()
                        .collect(Collectors.toMap(Student::getStudentId,
                                student -> studentService.haveScoreReport(student.getStudentId())));

        model.addAttribute("students", studentService.findStudents());
        model.addAttribute("profileStatus", profileStatus);
        model.addAttribute("scoreStatus", scoreStatus);
        return "students/list";
    }
}
