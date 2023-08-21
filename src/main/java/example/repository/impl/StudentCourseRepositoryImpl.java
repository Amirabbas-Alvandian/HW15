package example.repository.impl;

import example.base.repository.impl.BaseRepositoryImpl;
import example.entity.Course;
import example.entity.Student;
import example.entity.StudentCourse;
import example.repository.StudentCourseRepository;
import org.jetbrains.annotations.NotNull;

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
    }

    @Override
    public Optional<List<Course>> coursesOfStudentFromSpecificSemester(Student student, Integer term) {
        return Optional.of(getEntityManager().createQuery("select c from StudentCourse sc " +
                "join sc.course c where sc.student.id = :id and sc.semester = :term",Course.class)
                .setParameter("id",student.getId()).setParameter("term", term).getResultList());
    }

    @Override
    public List<StudentCourse> StudentGradeSheet(Student student) {
        return getEntityManager().createQuery("from StudentCourse sc where sc.student.id = :id"
                        , StudentCourse.class)
                .setParameter("id", student.getId()).getResultList();
    }

    ;
}
