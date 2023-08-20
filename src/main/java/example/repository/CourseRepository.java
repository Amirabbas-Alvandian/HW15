package example.repository;

import example.base.repository.BaseRepository;
import example.entity.Course;
import example.entity.Student;

import java.util.List;

public interface CourseRepository extends BaseRepository<Course> {
    List<Student> studentsOfCourse(Course course);
}
