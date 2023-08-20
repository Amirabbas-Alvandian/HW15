package example.UI;

import example.entity.Course;
import example.entity.Student;
import example.entity.Teacher;
import example.entity.TeacherRank;
import example.service.TeacherService;

import javax.persistence.EntityManager;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TeacherMenu {

    private final TeacherService teacherService;
    private final EntityManager entityManager;
    List<Integer> semesters = List.of(3981,3982,3991,3992,4001,4002,4011,4012,4021);

    Scanner scanner = new Scanner(System.in);

    private final double professorBaseSalary = 5_000_000.0;


    public TeacherMenu(TeacherService teacherService, EntityManager entityManager) {
        this.teacherService = teacherService;
        this.entityManager = entityManager;
    }

    public void choices(Teacher teacher){
        System.out.println("""
                1.Your info
                2.set student Scores
                3.payslip
                0.exit
                """);

        boolean flag = false;
        do{
            switch (scanner.nextLine()){
                case ("1") -> System.out.println(teacher);
                case ("2") -> setStudentScores(teacher);
                case ("3") -> payslip(teacher);
                case ("0") ->{
                    flag = true;
                }
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);
    }



    public void payslip(Teacher teacher){
        Integer term = getSemester();
        double salary = 0.0;
        if (teacher.getRank() == TeacherRank.PROFESSOR){
            salary +=  professorBaseSalary;
        }
        Optional<Long> unitsQuantity = teacherService.calculateUnits(teacher,term);
        if(unitsQuantity.isPresent()){
            System.out.println(teacher);
            salary += unitsQuantity.get()*1_000_000.0;
        }
        System.out.println(salary);
    }

    public void setStudentScores(Teacher teacher){
        boolean outerFlag = false;
        do{
            Integer term = getSemester();

            Course course = getCourse(teacher);
            boolean flag = false;
            do {
                System.out.print("choose student");
                Student student = getStudentOfCourse(course);

                System.out.print("score:");
                Double score = scanDouble();
                if (course.getId() != null)
                    teacherService.setStudentScore(term, student.getId(), course.getId(), score);

                System.out.println("1.another student\n2.change course or semester\n0.exit");
                switch (scanner.nextLine()) {
                    case ("1") -> System.out.println("next student");
                    case ("2") -> flag = true;
                    case ("0") -> {
                        return;
                    }
                    default -> System.out.println("wrong input try again");
                }
            } while (!flag);

            System.out.println("1.repeat\n0.exit");
            switch (scanner.nextLine()) {
                case ("1") -> System.out.println("NEXT");
                case ("0") -> outerFlag = true;
                default -> System.out.println("wrong input try again");
            }

        }while (!outerFlag);

    }


    public Student getStudentOfCourse (Course course){
        List<Student> studentList = teacherService.studentsOfCourse(course);
        for (int i = 0; i < studentList.size(); i++) {
            System.out.println(i+1 + "." + studentList.get(i));
        }
        System.out.println("Choose student");
        Student student = new Student();
        student.setId( null);
        while (student.getId() == null){
            try {
                student = studentList.get(scanInt());
            }catch (IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }
        }
        return student;
    }


    public Integer getSemester (){
        Integer term = null;
        for (int i = 0; i < semesters.size(); i++){
            System.out.println(i+1 + "." + semesters.get(i));
        }
        System.out.println("choose semester:");

        while (term == null){
            try {
                term = semesters.get(scanInt() - 1);
            }catch (IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
                System.out.println("mariz");
            }
        }
        return term;
    }

    public Course getCourse(Teacher teacher){
        List<Course> courses = teacherService.courseList(teacher);
        if (courses.isEmpty()){
            System.out.println("no courses in database");
            return null;
        }
        for (int i = 0; i < courses.size() ; i++) {
            System.out.println(i+1 + "." + courses.get(i));
        }
        System.out.println("choose course");
        Course course = new Course();
        course.setId(null);
        while (course.getId() == null){
            try {
                course = courses.get(scanInt());
            }catch (IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }
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


    public Double scanDouble (){
        Double score = null ;
        while (score == null){
            try {
                score = scanner.nextDouble();
                scanner.nextLine();
            }catch (InputMismatchException n){
                System.out.println(n.getMessage());
            }
        }
        return score;
    }
}
