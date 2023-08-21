package example.service;

import example.base.service.BaseService;
import example.entity.Course;
import example.entity.Student;

import java.util.List;

public interface CourseService extends BaseService<Course> {
    List<Student> studentsOfCourse(Course course,Integer semester);

}
