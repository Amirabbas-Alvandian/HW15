package example.util;

import example.Connection.EntityManagerClass;
import example.entity.*;
import example.repository.impl.*;
import example.service.*;
import example.service.impl.*;

import javax.persistence.EntityManager;

public class ApplicationContext {
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
        teacherService = new TeacherServiceImpl(teacherRepository,studentCourseService);
        employeeService = new EmployeeServiceImpl(employeeRepository,
                studentService,teacherService,courseService);
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
}
