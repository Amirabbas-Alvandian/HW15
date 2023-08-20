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
import example.validation.EntityValidation;
import lombok.Getter;

import javax.persistence.NoResultException;
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
    public List<StudentCourse> studentSemesterScores(int term, Long id) {
        return studentCourseService.studentSemesterCourses(term, id);
    }

    public Optional<Double> calculateStudentSemesterAverage(int term, Long id){
        return studentRepository.calculateStudentSemesterAverage(term, id);
    }

    @Override
    public void unitSelection(Student student, Course course, Integer semester) {
        StudentCourse studentCourse = new StudentCourse(student,course,semester);
        studentCourseService.save(studentCourse);
    }

    @Override
    public Optional<Student> findByCode(Integer code) {
        try {
            return studentRepository.findByCode(code);
        }catch (NoResultException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Course>> allCoursesPickedByStudent(Long id) {
        return studentRepository.allCoursesPickedByStudent(id);
    }

    @Override
    public Optional<Course> findCourse(Long id) {
        return courseService.find(id);
    }

    @Override
    public Optional<List<Course>> coursesOfStudentFromSpecificSemester(Student student, Integer term) {
        return studentCourseService.coursesOfStudentFromSpecificSemester(student,term);
    }


}
