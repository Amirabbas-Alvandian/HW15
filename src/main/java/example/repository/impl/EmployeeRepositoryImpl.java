package example.repository.impl;

import example.base.repository.impl.BaseRepositoryImpl;
import example.entity.Course;
import example.entity.Employee;
import example.entity.Student;
import example.entity.Teacher;
import example.repository.EmployeeRepository;
import example.service.CourseService;
import example.service.StudentService;
import example.service.TeacherService;
import example.util.ApplicationContext;
import lombok.Setter;

import javax.persistence.EntityManager;
import java.util.Optional;

@Setter
public class EmployeeRepositoryImpl extends BaseRepositoryImpl<Employee> implements EmployeeRepository {
    public EmployeeRepositoryImpl(EntityManager entityManager, Class<Employee> classname) {
        super(entityManager, classname);
    }

    public Optional<Employee> findByCode(Integer code){
        return Optional.ofNullable(getEntityManager().
                createQuery("from Employee where Employee .employeeCode =:code", Employee.class)
                        .setParameter("code",code)
                .getSingleResult());
    }

}
