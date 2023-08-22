package example.service.impl;


import example.base.service.impl.BaseServiceImpl;
import example.entity.Course;
import example.entity.Student;
import example.repository.CourseRepository;
import example.repository.impl.CourseRepositoryImpl;
import example.service.CourseService;
import example.validation.EntityValidation;


import javax.persistence.PersistenceException;
import javax.validation.Validator;
import java.util.List;

public class CourseServiceImpl extends BaseServiceImpl<Course> implements CourseService {
    CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepositoryImpl courseRepository) {
        super(courseRepository);
        this.courseRepository = courseRepository;
        Validator validator = EntityValidation.validator;
    }


    @Override
    public List<Student> studentsOfCourse(Course course,Integer semester) {
        try {
             return courseRepository.studentsOfCourse(course ,semester);
        }catch (NullPointerException | PersistenceException e  ){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
