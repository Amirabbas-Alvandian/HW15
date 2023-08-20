package example.repository.impl;

import example.base.repository.impl.BaseRepositoryImpl;
import example.entity.StudentCourse;
import example.repository.StudentCourseRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class StudentCourseRepositoryImpl extends BaseRepositoryImpl<StudentCourse> implements StudentCourseRepository {
    public StudentCourseRepositoryImpl(EntityManager entityManager, Class<StudentCourse> classname) {
        super(entityManager, classname);
    }

    public List<StudentCourse> studentSemesterCourses(int term, Long id) {
        return getEntityManager().createQuery("select sc from StudentCourse sc join sc.course " +
                        " where sc.semester = :term and sc.student.id = :id",StudentCourse.class).setParameter("term",term)
                .setParameter("id",id).getResultList();

    }
    @Override
    public Optional<StudentCourse> findWithoutId(int term, Long studentId, Long courseId){
        return Optional.ofNullable(getEntityManager().createQuery("from StudentCourse sc where sc.semester = :term" +
                        " and sc.student.id = :studentId and sc.course.id = : courseId", StudentCourse.class)
                .setParameter("studentId", studentId).setParameter("courseId", courseId)
                .setParameter("term", term)
                .getSingleResult());
    };
}
