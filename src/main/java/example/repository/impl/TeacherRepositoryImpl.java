package example.repository.impl;

import example.base.repository.impl.BaseRepositoryImpl;
import example.entity.Student;
import example.entity.StudentCourse;
import example.entity.Teacher;
import example.repository.TeacherRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class TeacherRepositoryImpl extends BaseRepositoryImpl<Teacher> implements TeacherRepository {
    public TeacherRepositoryImpl(EntityManager entityManager, Class<Teacher> classname) {
        super(entityManager, classname);
    }

    @Override
    public Optional<Teacher> findByCode(Integer code) {
        return Optional.of(getEntityManager()
                .createQuery("from Teacher where Teacher.teacherCode= :code", Teacher.class)
                .setParameter("code",code).getSingleResult());
    }
/*    @Override
    public int setStudentScore (int term, Long studentId, Long courseId,Double score){

        return getEntityManager().createQuery("update StudentCourse  set score = :score " +
                "where semester = :term and  student.id = :studentId and course.id = :courseId" )
                .setParameter("term",term).setParameter("studentId",studentId)
                .setParameter("score",score).setParameter("courseId",courseId).executeUpdate();

    }*/
}
