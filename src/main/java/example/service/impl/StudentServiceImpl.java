package example.service.impl;

import example.base.service.impl.BaseServiceImpl;
import example.entity.Course;
import example.entity.Student;
import example.entity.StudentCourse;
import example.repository.StudentRepository;
import example.repository.impl.StudentRepositoryImpl;
import example.service.CourseService;
import example.service.StudentCourseService;
import example.service.StudentService;
import example.util.ApplicationContext;
import example.validation.EntityValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

@Getter
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {
    private final CourseService courseService;
    private final StudentCourseService studentCourseService;
    private final StudentRepository studentRepository;
    private final Validator validator;
    public StudentServiceImpl(StudentRepositoryImpl studentRepository,
                              CourseService courseService,
                              StudentCourseService studentCourseService) {
        super(studentRepository);
        this.studentRepository = studentRepository;
        this.courseService = courseService;
        this.studentCourseService = studentCourseService;
        validator = EntityValidation.validator;
    }

    @Override
    public List<Course> seeAllCourses() {
        return courseService.findAll();
    }

    @Override
    public List<StudentCourse> studentSemesterCourses(int term, Long id) {
        return studentCourseService.studentSemesterCourses(term, id);
    }

    public double calculateStudentSemesterAverage(int term, Long id){
        return studentRepository.calculateStudentSemesterAverage(term, id);
    }

    @Override
    public void unitSelection(Student student, Course course, Integer semester) {
        StudentCourse studentCourse = new StudentCourse(student,course,semester);
        studentCourseService.save(studentCourse);
    }

    @Override
    public Optional<Student> findByCode(Integer code) {
        return studentRepository.findByCode(code);
    }


}
