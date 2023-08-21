package example.service;

import example.base.service.BaseService;
import example.entity.Course;
import example.entity.Student;
import example.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService extends BaseService<Teacher> {
    void setStudentScore (int term, Long studentId,Long courseId, Double score);

    Optional<Teacher> findByCode(Integer code);

    Optional<Long> calculateUnits (Teacher teacher,Integer semester);

    List<Course> courseList (Teacher teacher);

    List<Student> studentsOfCourse(Course course,Integer semester);

}
