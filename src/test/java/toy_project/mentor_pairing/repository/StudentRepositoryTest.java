package toy_project.mentor_pairing.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy_project.mentor_pairing.domain.Student;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void 학생등록조회() throws Exception {
        //given
        Student student = new Student();
        student.setStudentId(12345L);
        student.setName("이용균");
        //when
        studentRepository.save(student);
        //then
        Student findStudent = studentRepository.findOne(12345L);
        Assertions.assertThat(findStudent.getName()).isEqualTo("이용균");
    }
    
    @Test
    public void 학생전체조회() throws Exception {
        //given
        Student student1 = new Student();
        student1.setStudentId(10001L);
        student1.setName("김재민");

        Student student2 = new Student();
        student2.setStudentId(10002L);
        student2.setName("양승원");

        //when
        studentRepository.save(student1);
        studentRepository.save(student2);

        List<Student> studentList = studentRepository.findAll();

        //then
        Assertions.assertThat(studentList.size()).isEqualTo(2);
    }
}