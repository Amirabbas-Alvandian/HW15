package example.repository;

import example.base.repository.BaseRepository;
import example.entity.Employee;
import example.entity.Student;

import java.util.Optional;

public interface StudentRepository extends BaseRepository<Student> {
    double calculateStudentSemesterAverage(int term, Long id);

    Optional<Student> findByCode(Integer code);
}
