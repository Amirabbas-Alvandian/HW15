package example;

import javax.persistence.EntityManager;
import example.Connection.EntityManagerClass;
import example.UI.*;
import example.entity.*;
import example.service.*;
import example.util.ApplicationContext;

import java.util.*;


public class Main {

    public static Integer mainScanInt (){
        String temp;
        Integer integer = null ;
        while (integer == null){
            try {
                temp = mainScanner.nextLine();
                integer = Integer.parseInt(temp);
            }catch (InputMismatchException | NumberFormatException n){
                System.out.println(n.getMessage());
            }
        }
        return integer;
    }

    static Scanner mainScanner = new Scanner(System.in);
    public static void main(String[] args) {
        EntityManagerClass entityManagerClass = new EntityManagerClass();
        EntityManager entityManager = entityManagerClass.returnEm();
        StudentService studentService = ApplicationContext.getStudentService();
        CourseService courseService = ApplicationContext.getCourseService();
        TeacherService teacherService = ApplicationContext.getTeacherService();
        StudentCourseService studentCourseService = ApplicationContext.getStudentCourseService();
        EmployeeService employeeService = ApplicationContext.getEmployeeService();

        EmployeeMenu employeeMenu = new EmployeeMenu(employeeService,entityManager);
        StudentMenu studentMenu = new StudentMenu(studentService,entityManager);
        TeacherMenu teacherMenu = new TeacherMenu(teacherService,entityManager);
        SignIn signIn = ApplicationContext.getSignIn();


/*        Employee employee = new Employee();
        employee.setPassword("admin");
        employee.setFirstName("admin");
        employee.setLastName("adminZade");
        employee.setEmployeeCode(0);
        employeeService.save(employee);*/
        System.out.println("enter your code then password");
        signIn.typeDetermine(Main.mainScanInt(),Main.mainScanner.nextLine());















       // Optional<Student> student = new Student("Shoghal","koohi","ghar tarike",100,"Shotor giri");
        //studentService.save(student);
/*        Integer code = scanner.nextInt();
        scanner.nextLine();*/

/*        Employee admin = new Employee(0);
        admin.setPassword("admin");
        employeeService.save(admin);*/
/*        Teacher teacher = new Teacher("ashgar","gorbePaz","gonnaNadareKeGOORBE",TeacherRank.PROFESSOR,207);
        //teacherService.save(teacher);
        //System.out.println(employeeService.findStudentByCode(306));
        teacher.setId(12L);*/
        //Course course = new Course(Courses.MATH,teacher);
        //courseService.save(course);

/*        Optional<Course> course = courseService.find(19L);
        Optional<Student> student = studentService.find(11L);
        StudentCourse studentCourse = new StudentCourse(student.get(),course.get(),4011);
        studentCourseService.save(studentCourse);*/

        //System.out.println(teacherService.calculateUnits(teacher, 4011));
        //signIn.typeDetermine(207,"gonnaNadareKeGOORBE");


        //signIn.typeDetermine(178,"awddwd");

        /*Course testc = new Course();
        testc.setId(19L);
        Student tests = new Student();
        tests.setId(11L);*/
/*        entityManager.getTransaction().begin();

        Student student = entityManager.find(Student.class,2L);
        System.out.println(student);
        entityManager.getTransaction().commit();*/

        //employeeService.delete(27L);
        //System.out.println(studentCourseService.findAll());
/*        if (studentService.calculateStudentSemesterAverage(4011,11L).isPresent())
            System.out.println("test");*/
        //System.out.println();

        //System.out.println("hi");
/*        Teacher teacher = Teacher.builder().firstName("asghar").lastName("shalgham").rank(TeacherRank.PROFESSOR)
                .password("daawdawd").teacherCode(201).build();

        teacher.setCourses(List.of(course));
        Student student = Student.builder().studentNumber(100).firstName("shoghal")
                .lastName("koohi").major("asbSavari").password("felan").build();
        Student student1 = new Student("kargadan","kaftar","mar",102,"gh");*/

        //System.out.println(student1);
 /*       courseService.save(course);
        teacherService.save(teacher);*/
        //studentService.save(student1);

        //teacherService.setStudentScore(4011,10L,17);
/*        teacher.setId(1L);
        Course course1 = new Course(Courses.PROGRAMMING,teacher);
        courseService.save(course1);*/
/*        Optional<Student> student2 =studentService.find(2L);
        Student student3 = student2.get();
        Optional<Course> course2 = courseService.find(6L);
        Course course3 = course2.get();
        studentService.unitSelection(student3,course3,4011);*/

        //teacherService.setStudentScore(4011,2L,12.75);

        //System.out.println(studentService.calculateStudentSemesterAverage(4012,2L));

        //System.out.println(student1);
/*        student = studentService.find(2L);
        course = courseService.find(3L);*/

/*
        teacherService.save(teacher);
        Course course1 = new Course(Courses.GORAZ_SHENASI,teacher);
        courseService.save(course);
        student.setStudentNumber(199);
        studentService.save(student);
        */





/*        studentCourse.setScore(60.0);
        studentCourseService.save(studentCourse);
        teacher.setId(1L);*/
/*        System.out.println(teacherService.find(3L));
        System.out.println(teacherService.setStudentScore(4011, 12L, 16L, 5.25));*/
        // studentCourseService.save(studentCourse);
        //System.out.println(studentService.calculateStudentSemesterAverage(4011, 9L));
        //teacherService.setStudentScore(4011,30L,29L,20.1);
        //employeeService.saveCourse(new Course(Courses.CHEMISTRY,teacher1));

        // System.out.println(teacherService.findAll());


/*        System.out.println(student);
        System.out.println(course);*/
        //studentCourseService.save(studentCourse);
       // System.out.println(courseService.studentSemesterCourses(4011,2L));
        //System.out.println(studentCourseService.findAll());


        //System.out.println(employeeService.findAll());
/*        List<Course> courseList =  ApplicationContext.getCourseService().studentSemesterCourses(4011,2L);
        System.out.println(courseList);*/
        //System.out.println(courseService.findAll());
        //System.out.println(entityManager.createQuery("from Course", Course.class).getResultList());

        //System.out.println(ApplicationContext.getStudentCourseService().findAll());
/*        entityManager.getTransaction().begin();
        Student student1 = entityManager.find(student.getClass(),6L);
        Course course1 = entityManager.find(Course.class,5L);

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId(studentCourseId);
        studentCourse.setStudent(student1);
        studentCourse.setCourse(course1);
        //studentCourseId.setSemester(4012);

        //entityManager.persist(studentCourse);
        //entityManager.createQuery("from Employee")
        entityManager.getTransaction().commit();*/
    }

}