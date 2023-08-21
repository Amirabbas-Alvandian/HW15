package example.UI;

import example.entity.*;
import example.service.EmployeeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EmployeeMenu extends UsefulMethods{
    private final EmployeeService employeeService;
    private final EntityManager entityManager;

    public EmployeeMenu(EmployeeService employeeService, EntityManager entityManager) {
        this.employeeService = employeeService;
        this.entityManager = entityManager;
    }

    Scanner scanner = new Scanner(System.in);
    String[] classes = {"Teacher","Student","Employee","Course"};

    public void printer(String type){

        String string = "1.save"+ type + "\n2.update"+ type + "\n3.delete" +type +"\n0.exit";
        System.out.println(string);
    }

    public void chooseClass(Employee employee){
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

                case ("4") -> payslip(employee);

                case ("5") -> course();

                case ("0") -> flag = true;
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);
    }

    public void employee(){
        boolean flag = false;
        do {
            printer(classes[2]);
            switch (scanner.nextLine()){
                case ("1")-> employeeSave();
                case ("2")-> employeeUpdate();
                case ("3")-> employeeDelete();
                case ("0")-> flag = true;
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);

    }
    private void student() {
        boolean flag = false;
        do {
            printer(classes[1]);
            switch (scanner.nextLine()) {
                case ("1") -> studentSave();
                case ("2") -> studentUpdate();
                case ("3") -> studentDelete();
                case ("0") -> flag = true;
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);
    }
    private void teacher() {
        boolean flag = false;
        do {
            printer(classes[0]);
            switch (scanner.nextLine()) {
                case ("1") -> teacherSave();
                case ("2") -> teacherUpdate();
                case ("3") -> teacherDelete();
                case ("0") -> flag = true;
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);
    }

    public void course() {
        boolean flag = false;
        do {
            printer(classes[3]);
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
        boolean flag = true;
        do {
            Student student = new Student();
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

            student = employeeService.saveStudent(student);
            System.out.println(student);


            if (addMoreCheck())
                System.out.println("next");
            else
                flag = false;
        }while (flag);

    }

    public void studentUpdate(){
        System.out.print("student ID:");
        entityManager.getTransaction().begin();
        Student student = entityManager.find(Student.class,scanLong());
        if(student != null){
            System.out.println("""
                choose field to change
                1.first name
                2.last name
                3.password
                4.major
                0.exit""");

            switch (scanner.nextLine()){
                case ("1") ->{
                    System.out.println("new first name");
                    student.setFirstName(scanner.nextLine());
                    System.out.println("first name changed successfully");
                }
                case ("2") ->{
                    System.out.println("new last name");
                    student.setLastName(scanner.nextLine());
                    System.out.println("last name changed successfully");
                }
                case ("3") ->{
                    System.out.println("new password");
                    student.setPassword(scanner.nextLine());
                    System.out.println("password changed successfully");
                }
                case ("4") -> {
                    System.out.println("new major");
                    student.setMajor(scanner.nextLine());
                    System.out.println("major changed successfully");
                }

                case ("0") ->System.out.println("abort update");

                default -> System.out.println("wrong input try again");
            }
        }
        try {
            entityManager.getTransaction().commit();
        }catch (RollbackException e){
            System.out.println(e.getMessage());
        }
        System.out.println(student);
    }


    public void studentDelete(){
        System.out.println("student code:");
        Optional<Student> student = employeeService.findStudentByCode(scanInt());
        if (student.isEmpty())
            System.out.println("student not found");
        else
            employeeService.deleteStudent(student.get().getId());
    }

    public void teacherSave(){
        boolean flag = true;
        do {
            Teacher teacher = new Teacher();

            System.out.print("first name:");
            teacher.setFirstName(scanner.nextLine());

            System.out.print("last name:");
            teacher.setLastName(scanner.nextLine());

            while (teacher.getRank() == null){

                System.out.println("rank:");
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

            teacher = employeeService.saveTeacher(teacher);

            System.out.println(teacher);

            if (addMoreCheck()){
                System.out.println("next");
            }else
                flag = false;
        }while (flag);
    }

    public void teacherUpdate(){
        System.out.print("teacher ID");
        entityManager.getTransaction().begin();
        Teacher teacher = entityManager.find(Teacher.class,scanLong());
        if (teacher != null){
            System.out.println(teacher);

            System.out.println("""
                choose field to change
                1.first name
                2.last name
                3.password
                4.rank
                0.exit""");

            switch (scanner.nextLine()){
                case ("1") ->{
                    System.out.println("new first name");
                    teacher.setFirstName(scanner.nextLine());
                    System.out.println("first name changed successfully");
                }
                case ("2") ->{
                    System.out.println("new last name");
                    teacher.setLastName(scanner.nextLine());
                    System.out.println("last name changed successfully");
                }
                case ("3") ->{
                    System.out.println("new password");
                    teacher.setPassword(scanner.nextLine());
                    System.out.println("password changed successfully");
                }
                case ("4") -> teacherRankUpdate(teacher);

                case ("0") ->System.out.println("abort update");

                default -> System.out.println("wrong input try again");
            }
            System.out.println(teacher);
        }
        try {
            entityManager.getTransaction().commit();
        }catch (RollbackException r){
            System.out.println(r.getMessage());
        }


    }

    public void teacherDelete(){
        System.out.println("teacher code:");
        Optional<Teacher> teacher = employeeService.findTeacherByCode(scanInt());
        if (teacher.isEmpty())
            System.out.println("teacher not found");
        else
            employeeService.deleteTeacher(teacher.get().getId());
    }

    public void employeeSave(){
        boolean flag = true;
        do {
            Employee employee = new Employee();
            System.out.print("first name:");
            employee.setFirstName(scanner.nextLine());

            System.out.print("last name:");
            employee.setLastName(scanner.nextLine());

            System.out.print("password:");
            employee.setPassword(scanner.nextLine());

            System.out.print("employee code:");
            employee.setEmployeeCode(scanInt());

            employee = employeeService.save(employee);
            System.out.println(employee + " saved" );

            if (addMoreCheck()){
                System.out.println("next");
            }else
                flag = false;
        }while (flag);

    }

    public void employeeUpdate(){
        System.out.println("Employee ID:");
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class,scanLong());
        if(employee != null){
            System.out.println(employee);
            System.out.println("""
                    choose field to change
                    1.first name
                    2.last name
                    3.password
                    0.exit""");
            switch (scanner.nextLine()){
                case ("1") ->{
                    System.out.println("new first name");
                    employee.setFirstName(scanner.nextLine());
                    System.out.println("first name changed successfully");
                }
                case ("2") ->{
                    System.out.println("new last name");
                    employee.setLastName(scanner.nextLine());
                    System.out.println("last name changed successfully");
                }
                case ("3") ->{
                    System.out.println("new password");
                    employee.setPassword(scanner.nextLine());
                    System.out.println("password changed successfully");
                }
                case ("0") -> System.out.println("abort update");

                default -> System.out.println("wrong input try again");
            }
        }
        try {
            entityManager.getTransaction().commit();
        }catch (RollbackException r){
            System.out.println(r.getMessage());
        }
        System.out.println(employee);
    }

    public void employeeDelete(){
        System.out.println("Employee code:");
        Optional<Employee> employee = employeeService.findByCode(scanInt());
        if (employee.isEmpty())
            System.out.println("employee not found");
        else
            employeeService.delete(employee.get().getId());
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

    public void payslip(Employee employee){
/*        System.out.println("Employee code");
        Optional<Employee> employee = employeeService.findByCode(scanInt());
        if (employee.isEmpty()){
            System.out.println("employee not found");
            return;
        }*/
        System.out.println(employee + "\n"
                + "salary = " + employee.getBaseSalary());

    }

    public void courseSave(){
        boolean flag = false;
        do {
            Course course = new Course();
            System.out.println("1.predefined courses\n2.create new course\n0.exit");
            switch (scanner.nextLine()){
                case ("1") -> course = predefinedCourses(course);

                case ("2") -> course = newCourse(course);

                case ("0") -> flag = true;

                default -> System.out.println("wrong input try again");

            }
            course = chooseTeacher(course);
            course = employeeService.saveCourse(course);
            System.out.println(course);
        }while (!flag);
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
        if (course.isEmpty())
            System.out.println("course not found");
        else
            employeeService.deleteCourse(course.get().getId());
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


}
