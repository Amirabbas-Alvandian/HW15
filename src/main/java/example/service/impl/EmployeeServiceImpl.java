package example.service.impl;


import example.base.service.impl.BaseServiceImpl;
import example.entity.Course;
import example.entity.Employee;
import example.entity.Student;
import example.entity.Teacher;
import example.repository.EmployeeRepository;
import example.repository.impl.EmployeeRepositoryImpl;
import example.service.CourseService;
import example.service.EmployeeService;
import example.service.StudentService;
import example.service.TeacherService;
import example.validation.EntityValidation;


import javax.persistence.NoResultException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final EmployeeRepository employeeRepository;
    private final Validator validator;

    public EmployeeServiceImpl(EmployeeRepositoryImpl employeeRepository,
                               StudentService studentService,
                               TeacherService teacherService,
                               CourseService courseService
                               ) {
        super(employeeRepository);
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.employeeRepository = employeeRepository;
        validator = EntityValidation.validator;
    }

    @Override
    public void saveStudent(Student student) {
        studentService.save(student);
    }

    @Override
    public void saveTeacher(Teacher teacher) {
        teacherService.save(teacher);

    }


    @Override
    public void saveCourse(Course course) {
        courseService.save(course);

    }

    @Override
    public void deleteStudent(Long id) {
        studentService.delete(id);
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherService.delete(id);
    }

    @Override
    public void deleteCourse(Long id) {
        courseService.delete(id);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentService.update(student);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        return teacherService.update(teacher);

    }

    @Override
    public Course updateCourse(Course course) {
        return courseService.update(course);
    }

    @Override
    public Optional<Employee> findByCode(Integer code) {
        try {
            return employeeRepository.findByCode(code);
        }catch (NoResultException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> findStudentByCode(Integer code) {
        return studentService.findByCode(code);

    }

    @Override
    public Optional<Teacher> findTeacherByCode(Integer code) {
            return teacherService.findByCode(code);
    }

    @Override
    public Optional<Teacher> findTeacherById(Long id) {
        return teacherService.find(id);
    }

    public List<Teacher> AllTeachersList(){
        return teacherService.findAll();
    }

    @Override
    public Optional<Course> findCourse(Long id) {
        return courseService.find(id);
    }

    public List<Student> AllStudentsList(){
        return studentService.findAll();
    }

}
