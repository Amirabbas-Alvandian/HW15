package example.repository.impl;

import example.base.repository.impl.BaseRepositoryImpl;
import example.entity.Course;
import example.repository.CourseRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class CourseRepositoryImpl extends BaseRepositoryImpl<Course> implements CourseRepository {

    public CourseRepositoryImpl(EntityManager entityManager, Class<Course> classname) {
        super(entityManager, classname);

    }


}
