package example.service.impl;

import example.base.service.impl.BaseServiceImpl;
import example.entity.Course;
import example.entity.Student;
import example.entity.StudentCourse;
import example.repository.StudentCourseRepository;
import example.repository.impl.StudentCourseRepositoryImpl;
import example.service.StudentCourseService;
import example.validation.EntityValidation;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

public class StudentCourseServiceImpl extends BaseServiceImpl<StudentCourse> implements StudentCourseService {
    private final StudentCourseRepository studentCourseRepository;

    public StudentCourseServiceImpl(StudentCourseRepositoryImpl studentCourseRepository) {
        super(studentCourseRepository);
        this.studentCourseRepository = studentCourseRepository;
        Validator validator = EntityValidation.validator;
    }

    @Override
    public Optional<StudentCourse> findWithoutId(int term, Long studentId, Long courseId) {
        try{
            return studentCourseRepository.findWithoutId(term, studentId, courseId);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<StudentCourse> studentSemesterCourses(int term, Long id) {
        try {
            return studentCourseRepository.studentSemesterCourses(term,id);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<List<Course>> coursesOfStudentFromSpecificSemester(Student student, Integer term) {
        try {
            return studentCourseRepository.coursesOfStudentFromSpecificSemester(student, term);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<StudentCourse> StudentGradeSheet(Student student) {
        try {
            return studentCourseRepository.StudentGradeSheet(student);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public int deleteWithoutId(Integer semester, Long studentId, Long courseId) {
        getEntityManager().getTransaction().begin();
        try{
            return studentCourseRepository.deleteWithoutId(semester, studentId, courseId);
        }catch (PersistenceException | NullPointerException e){
            System.out.println(e.getMessage());
        }finally {
            getEntityManager().getTransaction().commit();
        }
        return 0;
    }
}
