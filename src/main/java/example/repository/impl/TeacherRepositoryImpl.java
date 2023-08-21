package example.repository.impl;

import example.base.repository.impl.BaseRepositoryImpl;
import example.entity.Course;
import example.entity.Student;
import example.entity.StudentCourse;
import example.entity.Teacher;
import example.repository.TeacherRepository;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class TeacherRepositoryImpl extends BaseRepositoryImpl<Teacher> implements TeacherRepository {
    public TeacherRepositoryImpl(EntityManager entityManager, Class<Teacher> classname) {
        super(entityManager, classname);
    }

    @Override
    public Optional<Teacher> findByCode(Integer code) {
        return Optional.of(getEntityManager()
                .createQuery("from Teacher t where t.teacherCode= :code", Teacher.class)
                .setParameter("code",code).getSingleResult());
    }

    @Override
    public Optional<Long> calculateUnits(@NotNull Teacher teacher, Integer semester) {
        return Optional.of(getEntityManager().createQuery("select sum(c.unit) from Teacher t" +
                " join t .courses c  join c.studentCourse sc where t.id = :id " +
                        "and sc.semester = :semester",Long.class).setParameter("semester",semester)
                .setParameter("id",teacher.getId()).getSingleResult());
    }

    @Override
    public List<Course> courseList(@NotNull Teacher teacher) {
        return getEntityManager().createQuery("select c from Teacher t join t.courses c" +
                " where c.teacher.id = :id",Course.class).setParameter("id",teacher.getId()).getResultList();
    }


/*    @Override
    public int setStudentScore (int term, Long studentId, Long courseId,Double score){

        return getEntityManager().createQuery("update StudentCourse  set score = :score " +
                "where semester = :term and  student.id = :studentId and course.id = :courseId" )
                .setParameter("term",term).setParameter("studentId",studentId)
                .setParameter("score",score).setParameter("courseId",courseId).executeUpdate();

    }*/
}
