package example.repository.impl;

import example.base.repository.impl.BaseRepositoryImpl;
import example.entity.Student;
import example.repository.StudentRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class StudentRepositoryImpl extends BaseRepositoryImpl<Student> implements StudentRepository {
    public StudentRepositoryImpl(EntityManager entityManager, Class<Student> classname) {
        super(entityManager, classname);
    }

    public double calculateStudentSemesterAverage(int term, Long id){
        return getEntityManager().createQuery("select sum(sc.score*c.unit)/sum(c.unit) from StudentCourse" +
                        " sc join sc.course c where sc .student.id = :id and sc.semester = :term",Double.class)
                .setParameter("term",term).setParameter("id",id).getSingleResult();

    }

    @Override
    public Optional<Student> findByCode(Integer code) {
        return Optional.of(getEntityManager()
                .createQuery("from Student where Student.studentNumber = :code",Student.class)
                .setParameter("code",code).getSingleResult());
    }

}
