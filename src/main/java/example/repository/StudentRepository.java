package example.repository;

import example.base.repository.BaseRepository;
import example.entity.Course;
import example.entity.Employee;
import example.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends BaseRepository<Student> {
    Optional<Double> calculateStudentSemesterAverage(int term, Long id);

    Optional<Student> findByCode(Integer code);

    Optional<List<Course>> allCoursesPickedByStudent(Long id);

}
