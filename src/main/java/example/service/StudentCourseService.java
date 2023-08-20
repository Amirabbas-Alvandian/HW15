package example.service;


import example.base.service.BaseService;
import example.entity.Course;
import example.entity.StudentCourse;

import java.util.List;
import java.util.Optional;

public interface StudentCourseService extends BaseService<StudentCourse> {

    Optional<StudentCourse> findWithoutId(int term, Long studentId, Long courseId);
    List<StudentCourse> studentSemesterCourses(int term, Long id);
}
