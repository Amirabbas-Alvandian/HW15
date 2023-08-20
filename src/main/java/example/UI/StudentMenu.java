package example.UI;

import example.entity.Course;
import example.entity.Student;
import example.service.StudentService;

import javax.persistence.EntityManager;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentMenu {
    StudentService studentService;
    EntityManager entityManager;
    Scanner scanner = new Scanner(System.in);


    List<Integer> semester = List.of(3981,3982,3991,3992,4001,4002,4011,4012,4021);

    public StudentMenu(StudentService studentService ,EntityManager entityManager) {
        this.studentService = studentService;
        this.entityManager = entityManager;
    }


    public void choices (Student student){
        boolean flag = false;
        do{
        System.out.println("""
                1.Your info
                2.All courses
                3.unit selection
                4.see grade sheet
                0.exit
                """);
            switch (scanner.nextLine()) {
                case ("1") -> System.out.println(student);
                case ("2") -> {
                    for (Course c : studentService.seeAllCourses()) {
                        System.out.println(c);
                    }
                }
                case ("3") -> unitSelect(student);

                case ("4") -> {
                    Optional<List<Course>> courseList = studentService.allCoursesPickedByStudent(student.getId());
                    if (courseList.isEmpty()) {
                        System.out.println("student doesnt have any courses");
                        return;
                    }
                    courseList.ifPresent(value -> value.forEach(System.out::println));
                }
                case ("0") -> {
                    flag = true;
                }
                default -> System.out.println("wrong input try again");
            }
        }while (!flag);
    }

    public void unitSelect(Student student){
        System.out.println("choose semester");
        Integer term = null;

        for (int i = 0; i< semester.size();i++){
            System.out.println(i+1 + "." + semester.get(i));
        }
        while (term == null){
            try{
            term = semester.get(scanInt());
            }catch (IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
                System.out.println("index out of bounds");
            }
        }


        List<Course> courseList = studentService.seeAllCourses();
        if (courseList.isEmpty()){
            System.out.println("there are no courses available");
            return;
        }


        Optional<List<Course>> studentCourses = studentService.coursesOfStudentFromSpecificSemester(student,term);
        int totalUnits = 0;
        if (studentCourses.isPresent()){
            totalUnits = studentCourses.get().stream().map(Course::getUnit).reduce(0, Integer::sum);
        }



        int counter = 0;
        for (Course c : courseList) {
            System.out.println(Integer.toString(++counter) + c);
        }
        Integer previousSemester = semester.indexOf(term) - 1;
        Integer max = calculateMaxUnits(student,previousSemester);
        while (totalUnits <max){
            System.out.println("choose course");
            Optional<Course> course = studentService.findCourse(scanLong());
            if (course.isPresent() && totalUnits + course.get().getUnit() <= max ){
                totalUnits += course.get().getUnit();
                studentService.unitSelection(student, course.get(), term);
            }else
                System.out.println("course doesnt exist");

            System.out.println("more units?\ny -> more courses\n n -> exit");
            switch (scanner.nextLine()){
                case ("n") -> {
                    return;
                }
                case ("y") -> System.out.println("you cant get " + (max - totalUnits) + " more units");

                default -> System.out.println("you have chosen DEATH!\npick more units as punishment");
            }
        }

        System.out.println();

    }


    public Integer calculateMaxUnits (Student student,Integer semester){

        Optional<Double> avg = studentService.calculateStudentSemesterAverage(semester,student.getId());
        if (avg.isPresent()){
            if(avg.get() >= 18.0){

                return 24;
            }
            return 18;
        }else
            System.out.println("student has no units for previous semester by default you can get upto 18");
        return 18;
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
}
