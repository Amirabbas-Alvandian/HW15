package example.service.impl;

import example.base.repository.impl.BaseRepositoryImpl;
import example.base.service.impl.BaseServiceImpl;
import example.entity.Course;
import example.repository.CourseRepository;
import example.repository.impl.CourseRepositoryImpl;
import example.service.CourseService;
import example.validation.EntityValidation;

import javax.validation.Validator;
import java.util.List;

public class CourseServiceImpl extends BaseServiceImpl<Course> implements CourseService {
    CourseRepository courseRepository;
    private final Validator validator;
    public CourseServiceImpl(CourseRepositoryImpl courseRepository) {
        super(courseRepository);
        this.courseRepository = courseRepository;
        this.validator = EntityValidation.validator;
    }


}
