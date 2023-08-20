package example.service;

import example.base.service.BaseService;
import example.entity.Course;
import example.entity.Employee;
import example.entity.Student;
import example.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface EmployeeService extends BaseService<Employee> {

    void saveStudent(Student student);
    void saveTeacher(Teacher teacher);
    void saveCourse (Course course);

    void deleteStudent(Long id);
    void deleteTeacher(Long id);
    void deleteCourse(Long id);

    Student updateStudent(Student student);
    Teacher updateTeacher(Teacher teacher);
    Course updateCourse(Course course);

    Optional<Employee> findByCode(Integer code);

    Optional<Student> findStudentByCode(Integer code);

    Optional<Teacher> findTeacherByCode(Integer code);

    Optional<Teacher> findTeacherById(Long id);

    List<Student> AllStudentsList();
    List<Teacher> AllTeachersList();
    Optional<Course> findCourse(Long id);




}
