package example.repository;

import example.base.repository.BaseRepository;
import example.entity.Course;
import example.entity.Employee;
import example.entity.Student;
import example.entity.Teacher;

import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee> {
    Optional<Employee> findByCode(Integer code);

}
