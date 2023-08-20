package example.repository.impl;

import example.base.repository.impl.BaseRepositoryImpl;
import example.entity.Course;
import example.entity.Student;
import example.repository.CourseRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class CourseRepositoryImpl extends BaseRepositoryImpl<Course> implements CourseRepository {

    public CourseRepositoryImpl(EntityManager entityManager, Class<Course> classname) {
        super(entityManager, classname);

    }


    @Override
    public List<Student> studentsOfCourse(Course course) {
        return getEntityManager().createQuery("select s from Course c" +
                " join c.studentCourse sc join Student s where c.id = :id",Student.class)
                .setParameter("id",course.getId()).getResultList();
    }
}
