package example.UI;

import example.entity.*;
import example.service.EmployeeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EmployeeMenu {
    private final EmployeeService employeeService;
    private final EntityManager entityManager;

    public EmployeeMenu(EmployeeService employeeService, EntityManager entityManager) {
        this.employeeService = employeeService;
        this.entityManager = entityManager;
    }

    Scanner scanner = new Scanner(System.in);
    String[] classes = {"Teacher","Student","Employee,Course"};

    public void printer(String type){

        String string = "1.save"+ type + "\n2.update"+ type + "\n3.delete" +type +"\n0.exit";
        System.out.println(string);
    }

    public void chooseClass(){
        boolean flag = false;
        do {
            System.out.println("""
                    choose menu
                    1.Teacher
                    2.Student
                    3.Employee
                    4.payslip
                    5.Course
                    0.exit""");
            switch (scanner.nextLine()) {
                case ("1") -> teacher();

                case ("2") -> student();

                case ("3") -> employee();

                case ("4") -> payslip();

                case ("5") -> course();

                case ("0") -> flag = true;
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);
    }

    public void employee(){
        printer(classes[2]);
        String choice = scanner.nextLine();
        boolean flag = false;
        do {
            switch (choice){
                case ("1")-> employeeSave();
                case ("2")-> employeeUpdate();
                case ("3")-> employeeDelete();
                case ("0")-> flag = true;
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);

    }
    private void student() {
        printer(classes[1]);
        String choice = scanner.nextLine();
        boolean flag = false;
        do {
            switch (choice) {
                case ("1") -> studentSave();
                case ("2") -> studentUpdate();
                case ("3") -> studentDelete();
                case ("0") -> flag = true;
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);
    }
    private void teacher() {
        printer(classes[0]);
        String choice = scanner.nextLine();
        boolean flag = false;
        do {
            switch (choice) {
                case ("1") -> teacherSave();
                case ("2") -> teacherUpdate();
                case ("3") -> teacherDelete();
                case ("0") -> flag = true;
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);
    }

    public void course() {
        printer(classes[3]);
        boolean flag = false;
        do {
            switch (scanner.nextLine()) {
                case ("1") -> courseSave();
                case ("2") -> courseUpdate();
                case ("3") -> courseDelete();
                case ("0") -> flag = true;
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);

    }


    public void studentSave(){
        Student student = new Student();
        while (student.getId() == null){

            System.out.print("first name:");
            student.setFirstName(scanner.nextLine());

            System.out.print("last name:");
            student.setLastName(scanner.nextLine());

            System.out.print("major:");
            student.setMajor(scanner.nextLine());

            System.out.print("password:");
            student.setPassword(scanner.nextLine());

            System.out.print("student code:");
            student.setStudentNumber(scanInt());

            employeeService.saveStudent(student);
        }
        System.out.println(student + " saved" );
    }

    public void studentUpdate(){
        System.out.print("student code:");
        entityManager.getTransaction().begin();
        Optional<Student> student = employeeService.findStudentByCode(scanInt());
        if(student.isPresent()){
            System.out.println("1.change Password\n2.change major\n3.exit");
            String choice = scanner.nextLine();
            try {
            switch (choice){
                case ("1")-> {
                    System.out.println("new password:");
                    student.get().setPassword(scanner.nextLine());
                    entityManager.getTransaction().commit();
                }
                case ("2")-> {
                    System.out.println("new Major:");
                    student.get().setMajor(scanner.nextLine());
                    entityManager.getTransaction().commit();
                }
                case ("0") -> {
                    return;
                }
                default -> System.out.println("wrong input try again");
            }

            }catch (PersistenceException p){
                System.out.println(p.getMessage());
            }
        }
        System.out.println("student not found");
    }


    public void studentDelete(){
        System.out.println("student code:");
        Optional<Student> student = employeeService.findStudentByCode(scanInt());
        student.ifPresent(value -> employeeService.deleteStudent(value.getId()));
        if (student.isEmpty())
            System.out.println("student not found");
    }

    public void teacherSave(){
        Teacher teacher = new Teacher();
        while (teacher.getId() == null){

            System.out.print("first name:");
            teacher.setFirstName(scanner.nextLine());

            System.out.print("last name:");
            teacher.setLastName(scanner.nextLine());

            while (teacher.getRank() == null){

                System.out.print("rank:");
                int counter = 1;
                for (TeacherRank c: TeacherRank.values()) {
                    System.out.println(Integer.toString(counter) + c);
                    counter++;
                }
                String choice = scanner.nextLine();
                switch (choice){
                    case ("1")-> teacher.setRank(TeacherRank.PROFESSOR);

                    case ("2")-> teacher.setRank(TeacherRank.COACH);

                    case ("3")-> teacher.setRank(TeacherRank.TEACHER);

                    default -> System.out.println("wrong input try again");
                }
            }
            System.out.print("password:");
            teacher.setPassword(scanner.nextLine());

            System.out.print("teacher code:");
            teacher.setTeacherCode(scanInt());

            employeeService.saveTeacher(teacher);
        }
        System.out.println(teacher + " saved" );
    }

    public void teacherUpdate(){
        System.out.print("teacher Code");
        entityManager.getTransaction().begin();
        Optional<Teacher> teacher = employeeService.findTeacherByCode(scanInt());
        if (teacher.isPresent()){
            boolean flag = true;
            do {
                System.out.println("1.update password\n2.update rank");
                String choice = scanner.nextLine();
                switch (choice) {
                    case ("1") -> {
                        System.out.println("new password");
                        teacher.get().setPassword(scanner.nextLine());
                        entityManager.getTransaction().commit();
                    }

                    case ("2") -> {
                        teacherRankUpdate(teacher.get());
                        entityManager.getTransaction().commit();
                    }

                    case ("0") ->{
                        System.out.println("update aborted");
                        flag = false;
                    }

                    default -> System.out.println("wrong input try again");
                }
            }while (!flag);
            System.out.println("teacher not found");
            entityManager.getTransaction().commit();
        }

    }

    public void teacherDelete(){
        System.out.println("teacher code:");
        Optional<Teacher> teacher = employeeService.findTeacherByCode(scanInt());
        if (teacher.isEmpty()){
            System.out.println("teacher not found");
            return;
        }
        teacher.ifPresent(value -> employeeService.deleteTeacher(value.getId()));
    }

    public void employeeSave(){
        Employee employee = new Employee();
        while (employee.getId() == null){
            System.out.print("first name:");
            employee.setFirstName(scanner.nextLine());

            System.out.print("last name:");
            employee.setLastName(scanner.nextLine());

            System.out.print("password:");
            employee.setPassword(scanner.nextLine());

            System.out.print("employee code:");
            employee.setEmployeeCode(scanInt());

            employeeService.save(employee);
        }
        System.out.println(employee + " saved" );
    }

    public void employeeUpdate(){
        System.out.println("Employee code:");
        entityManager.getTransaction().begin();
        Optional<Employee> employee = employeeService.findByCode(scanner.nextInt());
        if(employee.isPresent()){
            System.out.println("new password");
            employee.get().setPassword(scanner.nextLine());
            entityManager.getTransaction().commit();
            System.out.println("password changed successfully");
            return;
        }
        entityManager.getTransaction().commit();
        System.out.println("employee not found");
    }

    public void employeeDelete(){
        System.out.println("Employee code:");
        Optional<Employee> employee = employeeService.findByCode(scanInt());
        if (employee.isEmpty()){
            System.out.println("employee not found");
            return;
        }
        employee.ifPresent(value -> employeeService.delete(value.getId()));
    }


    public void teacherRankUpdate(Teacher teacher){

        int counter = 1;
        for (TeacherRank c: TeacherRank.values()) {
            System.out.println(Integer.toString(counter) + c);
            counter++;
        }
        System.out.println("new rank:");
        String choice = scanner.nextLine();
        boolean flag = true;
        do  {
            switch (choice){
                case ("1") -> teacher.setRank(TeacherRank.PROFESSOR);

                case ("2") -> teacher.setRank(TeacherRank.COACH);

                case ("3") -> teacher.setRank(TeacherRank.TEACHER);

                default ->{
                    System.out.println("wrong input try again");
                    flag = false;
                }
            }
        }while (!flag);
    }

    public void payslip(){
        System.out.println("Employee code");
        Optional<Employee> employee = employeeService.findByCode(scanInt());
        if (employee.isEmpty()){
            System.out.println("employee not found");
            return;
        }
        employee.ifPresent(value -> System.out.println( value + "\n"
                + "salary = " + value.getBaseSalary() ));
    }

    public void courseSave(){
        Course course = new Course();
        System.out.println("1.predefined courses\n2.create new course\n0.exit");
        boolean flag = false;
        do {
            switch (scanner.nextLine()){
                case ("1") -> course = predefinedCourses(course);

                case ("2") -> course = newCourse(course);

                case ("0") -> flag = true;

                default -> System.out.println("wrong input try again");

            }
        }while (!flag);
        course = chooseTeacher(course);

        employeeService.saveCourse(course);
    }

    public void courseUpdate(){
        System.out.println("course id:");
        Long id = scanLong();
        entityManager.getTransaction().begin();
        Optional<Course> course = employeeService.findCourse(id);
        if(course.isPresent()){
            System.out.println(course);
            System.out.println("1.update unit\n2.update course name\n0.exit");
            boolean flag = false;
            do {
                switch (scanner.nextLine()) {
                    case ("1") -> {
                        System.out.println("new unit:");
                        Integer unit = scanInt();
                        while (unit >= 4 || unit < 1) {
                            System.out.println("unit should be less than 4 and more than 0");
                            unit = scanInt();
                        }
                        course.get().setUnit(unit);
                    }
                    case ("2") -> {
                        System.out.println("new name:");
                        course.get().setCourseName(scanner.nextLine());
                    }
                    case ("0") -> {
                        System.out.println("abort update");
                        flag = true;
                    }

                    default -> System.out.println("wrong input try again");
                }
            }while (!flag);
        }
    }

    public void courseDelete(){
        System.out.print("Course ID:");
        Optional<Course> course = employeeService.findCourse(scanLong());
        if(course.isEmpty()){
            System.out.println("course not found");
            return;
        }
        course.ifPresent(value -> employeeService.deleteCourse(value.getId()));
    }

    public Course predefinedCourses(Course course){
        int counter = 0;
        for (Courses c : Courses.values())
            System.out.println(Integer.toString(++counter) + c);
        while (course.getCourseName() == null) {
            switch (scanner.nextLine()) {
                case ("1") -> {
                    course.setCourseName(Courses.MATH.name());
                    course.setUnit(Courses.MATH.getUnit());
                }
                case ("2") -> {
                    course.setCourseName(Courses.CHEMISTRY.name());
                    course.setUnit(Courses.CHEMISTRY.getUnit());
                }
                case ("3") -> {
                    course.setCourseName(Courses.PHYSICS.name());
                    course.setUnit(Courses.PHYSICS.getUnit());
                }
                case ("4") -> {
                    course.setCourseName(Courses.PROGRAMMING.name());
                    course.setUnit(Courses.PROGRAMMING.getUnit());
                }
                case ("5") -> {
                    course.setCourseName(Courses.LITERATURE.name());
                    course.setUnit(Courses.LITERATURE.getUnit());
                }
                case ("6") -> {
                    course.setCourseName(Courses.GORAZ_SHENASI.name());
                    course.setUnit(Courses.GORAZ_SHENASI.getUnit());
                }
                default -> System.out.println("wrong input try again");
                }
            }

            return course;
    }

    public Course newCourse(Course course){

        System.out.println("course name:");
        course.setCourseName(scanner.nextLine());
        System.out.println("course unit:");

        Integer unit = scanInt();
        while (unit >= 4 || unit < 1){
            System.out.println("unit should be less than 4 and more than 0");
            unit = scanInt();
        }
        course.setUnit(unit);
        return course;
    }


    public Course chooseTeacher(Course course) {
        int counter = 0;
        List<Teacher> teacherList = employeeService.AllTeachersList();
        for (Teacher t : teacherList) {
            System.out.println(Integer.toString(++counter) + t);
        }
        System.out.println("choose teacher");
        while (course.getTeacher() == null){
            Optional<Teacher> teacher = employeeService.findTeacherById(scanLong());
            if (teacher.isEmpty()){
                System.out.println("wrong teacher id");
            }
            teacher.ifPresent(course::setTeacher);


            //ASK
            /*try {
                String index = scanner.nextLine();
                course.setTeacher(teacherList.get(Integer.parseInt(index) - 1));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
                System.out.println("enter correct index");
            }catch (NumberFormatException | InputMismatchException n){
                System.out.println(n.getMessage());
                System.out.println("enter number only");
            }*/
    }
        return course;
    }

    public Integer scanInt (){
        Integer integer = null ;
        while (integer == null){
            try {
                integer = scanner.nextInt();
                scanner.nextLine();
            }catch (InputMismatchException n){
                System.out.println(n.getMessage());
            }
        }
        return integer;
    }

    public Long scanLong (){
        Long tempLong = null;
        while (tempLong == null){
            try {
                tempLong = scanner.nextLong();
                scanner.nextLine();
            }catch (InputMismatchException n){
                System.out.println(n.getMessage());
            }
        }
        return tempLong;
    }

}
