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
import javax.persistence.RollbackException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final EmployeeRepository employeeRepository;

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
        Validator validator = EntityValidation.validator;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentService.save(student);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        return teacherService.save(teacher);

    }


    @Override
    public Course saveCourse(Course course) {
        return courseService.save(course);
    }

    @Override
    public void deleteStudent(Long id) {
        try {
            studentService.delete(id);
        }catch (RollbackException r){
            System.out.println(r.getMessage());
            System.out.println("foreign key");
        }

    }

    @Override
    public void deleteTeacher(Long id) {
        try {
            teacherService.delete(id);
        }catch (RollbackException r){
            System.out.println(r.getMessage());
            System.out.println("foreign key");
        }

    }

    @Override
    public void deleteCourse(Long id) {
        try {
            courseService.delete(id);
        }catch (RollbackException r){
            System.out.println(r.getMessage());
            System.out.println("foreign key");
        }

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
