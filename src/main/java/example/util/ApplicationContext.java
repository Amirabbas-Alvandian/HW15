package example.util;

import example.Connection.EntityManagerClass;
import example.UI.EmployeeMenu;
import example.UI.SignIn;
import example.UI.StudentMenu;
import example.UI.TeacherMenu;
import example.entity.*;
import example.repository.impl.*;
import example.service.*;
import example.service.impl.*;

import javax.persistence.EntityManager;

public class ApplicationContext {
    static SignIn signIn;
    static EmployeeMenu employeeMenu;
    static StudentMenu studentMenu;
    static TeacherMenu teacherMenu;
    static EntityManagerClass managerClass = new EntityManagerClass();
    static EntityManager entityManager = managerClass.returnEm();
    static CourseRepositoryImpl courseRepository;
    static EmployeeRepositoryImpl employeeRepository;
    static StudentRepositoryImpl studentRepository;
    static TeacherRepositoryImpl teacherRepository;
    static StudentCourseRepositoryImpl studentCourseRepository;
    static StudentCourseService studentCourseService;

    static CourseService courseService;
    static EmployeeService employeeService;
    static StudentService studentService;
    static TeacherService teacherService;

    static {
        courseRepository = new CourseRepositoryImpl(entityManager, Course.class);

        employeeRepository = new EmployeeRepositoryImpl(entityManager, Employee.class);

        studentRepository = new StudentRepositoryImpl(entityManager, Student.class);

        teacherRepository = new TeacherRepositoryImpl(entityManager, Teacher.class);

        studentCourseRepository = new StudentCourseRepositoryImpl(entityManager, StudentCourse.class);

        studentCourseService = new StudentCourseServiceImpl(studentCourseRepository);

        courseService = new CourseServiceImpl(courseRepository);

        studentService = new StudentServiceImpl(studentRepository,
                courseService,studentCourseService);

        teacherService = new TeacherServiceImpl(teacherRepository,studentCourseService,courseService);

        employeeService = new EmployeeServiceImpl(employeeRepository,
                studentService,teacherService,courseService);

        employeeMenu = new EmployeeMenu(employeeService,entityManager);

        studentMenu = new StudentMenu(studentService,entityManager);

        teacherMenu = new TeacherMenu(teacherService,entityManager);

        signIn = new SignIn(employeeService,studentService,teacherService,employeeMenu,studentMenu,teacherMenu);
    }

    public static CourseService getCourseService() {
        return courseService;
    }

    public static EmployeeService getEmployeeService() {
        return employeeService;
    }

    public static StudentService getStudentService() {
        return studentService;
    }

    public static TeacherService getTeacherService() {
        return teacherService;
    }

    public static StudentCourseService getStudentCourseService() {
        return studentCourseService;
    }

    public static SignIn getSignIn() {
        return signIn;
    }
}
