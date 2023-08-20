package example.repository;

import example.base.repository.BaseRepository;
import example.entity.Employee;
import example.entity.StudentCourse;
import example.entity.Teacher;

import java.util.Optional;

public interface TeacherRepository extends BaseRepository<Teacher> {

    Optional<Teacher> findByCode(Integer code);
}
