package example.service;

import example.base.service.BaseService;
import example.entity.Course;
import example.entity.Student;
import example.entity.StudentCourse;
import example.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface StudentService extends BaseService<Student> {
    List<Course> seeAllCourses();
    List<StudentCourse> studentSemesterScores(int term,Long id);
    Optional<Double> calculateStudentSemesterAverage(int term, Long id);
    void unitSelection(Student student,Course course,Integer semester);
    Optional<Student> findByCode(Integer code);
    Optional<List<Course>> allCoursesPickedByStudent(Long id);
    Optional<Course> findCourse (Long id);

    Optional<List<Course>> coursesOfStudentFromSpecificSemester (Student student, Integer term);

    Optional<StudentCourse> getScore (Student student, Course course);

    List<StudentCourse> StudentGradeSheet(Student student);


}
