package example.service.impl;


import example.base.service.impl.BaseServiceImpl;
import example.entity.Course;
import example.entity.Student;
import example.entity.StudentCourse;
import example.entity.Teacher;
import example.repository.TeacherRepository;
import example.repository.impl.TeacherRepositoryImpl;
import example.service.CourseService;
import example.service.StudentCourseService;
import example.service.TeacherService;
import example.validation.EntityValidation;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

public class TeacherServiceImpl extends BaseServiceImpl<Teacher> implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final CourseService courseService;

    private final StudentCourseService studentCourseService;
    public TeacherServiceImpl(TeacherRepositoryImpl teacherRepository,
                              StudentCourseService studentCourseService,
                              CourseService courseService) {
        super(teacherRepository);
        this.teacherRepository = teacherRepository;
        this.studentCourseService = studentCourseService;
        Validator validator = EntityValidation.validator;
        this.courseService = courseService;
    }


    public void setStudentScore (int term, Long studentId,Long courseId, Double score){
        getEntityManager().getTransaction().begin();
        try {
        Optional<StudentCourse> studentCourse = studentCourseService.findWithoutId(term, studentId,courseId);
        if (studentCourse.isPresent()){
            System.out.println("student found");
            studentCourse.get().setScore(score);
        }else{
            System.out.println("student not found");
        }
        getEntityManager().getTransaction().commit();
        System.out.println("success");
        System.out.println(studentCourse);
        }catch (PersistenceException e){
            System.out.println(e.getMessage());
            getEntityManager().getTransaction().rollback();
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Teacher> findByCode(Integer code) {
        try {
            return teacherRepository.findByCode(code);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Long> calculateUnits(Teacher teacher,Integer semester) {
        try{
            return teacherRepository.calculateUnits(teacher,semester);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Course> courseList(Teacher teacher,Integer semester) {
        try{
            return teacherRepository.courseList(teacher,semester);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Student> studentsOfCourse(Course course,Integer semester) {
        try{
             return courseService.studentsOfCourse(course,semester);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}
