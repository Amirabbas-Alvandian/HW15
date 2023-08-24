package example.UI;

import example.entity.Course;
import example.entity.Student;
import example.entity.StudentCourse;
import example.service.StudentService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.*;

public class StudentMenu extends UsefulMethods{
    StudentService studentService;
    EntityManager entityManager;
    Scanner scanner = new Scanner(System.in);


    List<Integer> semester = List.of(4012,4021);

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
                    List<StudentCourse> courseList = studentService.StudentGradeSheet(student);
                    if (courseList.isEmpty()) {
                        System.out.println("student doesnt have any courses");
                    }else
                        courseList.forEach(System.out::println);
                }
                case ("0") -> flag = true;

                default -> System.out.println("wrong input try again");
            }
        }while (!flag);
    }

    public void unitSelect(Student student){
        System.out.println("semester 4021");
        Integer term = semester.get(1);

        System.out.println("""
                1.pick unit
                2.delete unit
                0.exit
                """);
        switch (scanner.nextLine()){
            case ("1")-> chooseCourse(student,term);
            case ("2")-> deleteCourse(student,term);
            case ("0")-> System.out.println("aborting unit selection");
            default -> System.out.println("wrong input try again");
        }
/*
        for (int i = 0; i< semester.size();i++){
            System.out.println(i+1 + "." + semester.get(i));
        }

        while (term == null){
            try{
            term = semester.get(scanInt() - 1);
            }catch (IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
                System.out.println("index out of bounds");
            }
        }
        System.out.println(term);*/

        System.out.println("finish");
    }

    private void deleteCourse(Student student, Integer term) {
        List<Course> courseList = allStudentCourses(student);
        if (courseList.isEmpty()){
            System.out.println("you dont have any courses");
            return;
        }
        for (Course c:courseList) {
            System.out.println(c);
        }
        System.out.print("course Id:");
        System.out.println(studentService.deleteStudentCourse(term, student.getId(), scanLong()));

    }


    public Integer calculateMaxUnits (Student student,Integer semester){

        Optional<Double> avg = studentService.calculateStudentSemesterAverage(semester,student.getId());
        if (avg.isPresent()){
            if(avg.get() >= 18.0){
                System.out.println("Last semester average: " + avg.get() +  " you can pick upto 24 units");
                return 24;
            }
            System.out.println("average below 18 you can pick upto 20 units");
            return 20;
        }else
            System.out.println("student has no units for previous semester by default you can get upto 20");
        return 20;
    }

    private void chooseCourse (Student student,Integer term){

        if(!doesAnyCourseExist()){
            System.out.println("no courses available in database");
            return;
        }



        Integer totalUnits = getTotalUnits(student,term);

        Integer previousSemester = semester.get(semester.indexOf(term) - 1);

        System.out.println(previousSemester);

        Integer max = calculateMaxUnits(student,previousSemester);


        while (totalUnits <max){
            System.out.println("all student courses BEGIN");
            List<Course> previousCoursesOfStudent = allStudentCourses(student);
            System.out.println(previousCoursesOfStudent);
            System.out.println("all student courses END");
            totalUnits = getTotalUnits(student,term);
            printAllAvailableCourses();
            System.out.println("choose course ID");
            Optional<Course> course = studentService.findCourse(scanLong());
            if (course.isPresent() && repeatedCourseAddCheck(previousCoursesOfStudent,course.get(),student)
                    && maxUnitsCheck(totalUnits,max,course.get()) ){

                totalUnits += course.get().getUnit();
                try {
                    studentService.unitSelection(student, course.get(), term);
                }catch (PersistenceException | IllegalStateException  p ){
                    System.out.println(p.getMessage());
                }


            }else
                System.out.println("course doesnt exist or you cant get the course");

            System.out.println("more units?\ny -> more courses\n n -> exit");
            switch (scanner.nextLine()){
                case ("n") -> {
                    return;
                }
                case ("y") -> System.out.println("you cant get " + (max - totalUnits) + " more units");

                default -> System.out.println("you have chosen DEATH!\npick more units as punishment");
            }
        }
        System.out.println("max units picked");
    }


    public Integer getTotalUnits(Student student,Integer term){
        Optional<List<Course>> studentCourses = studentService.coursesOfStudentFromSpecificSemester(student,term);
        int totalUnits = 0;
        if (studentCourses.isPresent()){
            totalUnits = studentCourses.get().stream().map(Course::getUnit).reduce(0, Integer::sum);
        }
        return totalUnits;
    }



    public boolean doesAnyCourseExist(){
        List<Course> courseList = studentService.seeAllCourses();
        if (courseList.isEmpty()){
            System.out.println("there are no courses available");
            return false;
        }
        return true;
    }

    public void printAllAvailableCourses(){
        for (Course c : studentService.seeAllCourses()) {
            System.out.println(c);
        }
    }


    public List<Course> allStudentCourses (Student student){
        return studentService.allCoursesPickedByStudent(student.getId())
                .orElse(null);
    }

    public boolean repeatedCourseAddCheck(List<Course> previousCourses, Course course,Student student){

        for (Course previousCourse : previousCourses) {
            if (previousCourse.getCourseName().equals(course.getCourseName()))
                return checkScore(student, course);
        }
            return true;
    }


    public boolean maxUnitsCheck (Integer totalUnits, Integer max,Course course){
        return course.getUnit() + totalUnits <= max;
    }
    public Boolean checkScore (Student student,Course course){
        Optional<StudentCourse> studentCourse = studentService.getScore(student,course);
        if(studentCourse.isPresent()){
            if (studentCourse.get().getScore() == null){
                System.out.println("you have already picked this course");
                return false;
            }
            Double score = studentCourse.get().getScore();
            if (score < 10){
                System.out.println("you have failed this course in the past you may pick it again");
                return true;
            }
            System.out.println("you have already passed this course in the past you cant pick it again");
            return false;
        }
        System.out.println("first time picking this course");
        return true;
    }

}
