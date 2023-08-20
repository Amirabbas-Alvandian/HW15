package example.repository;

import example.base.repository.BaseRepository;
import example.entity.Course;
import example.entity.Student;
import example.entity.StudentCourse;

import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends BaseRepository<StudentCourse> {
    List<StudentCourse> studentSemesterCourses(int term, Long id);
    Optional<StudentCourse> findWithoutId(int term, Long studentId, Long courseId);

    Optional<List<Course>> coursesOfStudentFromSpecificSemester (Student student, Integer term);
}
