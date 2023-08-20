package example.service.impl;

import example.base.service.impl.BaseServiceImpl;
import example.entity.StudentCourse;
import example.repository.StudentCourseRepository;
import example.repository.impl.StudentCourseRepositoryImpl;
import example.service.StudentCourseService;
import example.validation.EntityValidation;

import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

public class StudentCourseServiceImpl extends BaseServiceImpl<StudentCourse> implements StudentCourseService {
    private final StudentCourseRepository studentCourseRepository;
    private final Validator validator;
    public StudentCourseServiceImpl(StudentCourseRepositoryImpl studentCourseRepository) {
        super(studentCourseRepository);
        this.studentCourseRepository = studentCourseRepository;
        validator = EntityValidation.validator;
    }

    @Override
    public Optional<StudentCourse> findWithoutId(int term, Long studentId, Long courseId) {
        return studentCourseRepository.findWithoutId(term, studentId, courseId);
    }

    @Override
    public List<StudentCourse> studentSemesterCourses(int term, Long id) {
        return studentCourseRepository.studentSemesterCourses(term,id);
    }
}
