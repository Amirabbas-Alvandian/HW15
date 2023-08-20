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
    List<StudentCourse> studentSemesterCourses(int term,Long id);
    double calculateStudentSemesterAverage(int term, Long id);

    void unitSelection(Student student,Course course,Integer semester);

    Optional<Student> findByCode(Integer code);
}
