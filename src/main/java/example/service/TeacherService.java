package example.service;

import example.base.service.BaseService;
import example.entity.Teacher;

import java.util.Optional;

public interface TeacherService extends BaseService<Teacher> {
    void setStudentScore (int term, Long studentId,Long courseId, Double score);

    Optional<Teacher> findByCode(Integer code);
}
