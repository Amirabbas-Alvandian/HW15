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
import javax.persistence.PersistenceException;
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
        try {
            return studentRepository.calculateStudentSemesterAverage(term, id);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();

    }

    @Override
    public void unitSelection(Student student, Course course, Integer semester) {
        StudentCourse studentCourse = new StudentCourse(student,course,semester);
        try {
            //System.out.println("update");
            studentCourseService.update(studentCourse);
        }catch (PersistenceException | IllegalStateException  p ){
            System.out.println(p.getMessage());
/*            if (p.getMessage().equals("No entity found for query")){
                System.out.println("save");
                studentCourseService.save(studentCourse);
            }*/

        }

    }

    @Override
    public Optional<Student> findByCode(Integer code) {
        try {
            return studentRepository.findByCode(code);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Course>> allCoursesPickedByStudent(Long id) {
        try {
            return studentRepository.allCoursesPickedByStudent(id);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Course> findCourse(Long id) {
        try {
            return courseService.find(id);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Course>> coursesOfStudentFromSpecificSemester(Student student, Integer term) {
        try {
            return studentCourseService.coursesOfStudentFromSpecificSemester(student,term);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<StudentCourse> getScore(Student student, Course course) {
        try {
            return studentRepository.getScore(student,course);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<StudentCourse> StudentGradeSheet(Student student) {
        return studentCourseService.StudentGradeSheet(student);
    }

    @Override
    public int deleteStudentCourse(Integer semester, Long studentId, Long courseId) {
        Optional<StudentCourse> studentCourse = studentCourseService.findWithoutId(semester, studentId, courseId);
        if (studentCourse.isPresent()){
            System.out.println("found studentCourse");
            return studentCourseService.deleteWithoutId(semester, studentId, courseId);
        }
        System.out.println("student course not found");
        return 0;
    }

    @Override
    public Optional<StudentCourse> findStudentCourse(Long studentId, Long courseId, Integer semester) {
        return studentCourseService.findWithoutId(semester,studentId,courseId);
    }


}
