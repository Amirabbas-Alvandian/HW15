package example.repository;

import example.base.repository.BaseRepository;
import example.entity.*;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends BaseRepository<Teacher> {

    Optional<Teacher> findByCode(Integer code);

    Optional<Long> calculateUnits (Teacher teacher,Integer semester);

    List<Course> courseList (Teacher teacher);
}
