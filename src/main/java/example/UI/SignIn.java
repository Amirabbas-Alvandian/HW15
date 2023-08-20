package example.UI;

import example.entity.Employee;
import example.entity.Student;
import example.entity.Teacher;
import example.service.EmployeeService;
import example.service.StudentService;
import example.service.TeacherService;

import java.util.Optional;

public class SignIn {
    private EmployeeService employeeService;
    private StudentService studentService;
    private TeacherService teacherService;

    private EmployeeMenu gotoEmployeeMenu;
    public void typeDetermine(Integer code,String password){
        if (code > 0 && code < 100) {
            employeeMenu(code, password);
        } else if (code < 200) {
            studentMenu(code, password);
        } else if (code < 300) {
            teacherMenu(code, password);
        } else {
            System.out.println("code out of any class range");
        }
    }

    public void employeeMenu(Integer code,String password){
        Optional<Employee> result = employeeService.findByCode(code);
        if (result.isPresent()){
            if (result.get().getPassword().equals(password))
                gotoEmployeeMenu.chooseClass();
            else
                System.out.println("wrong password");
        }
        System.out.println("employee code does not exist");
    }

    public Student studentMenu(Integer code,String password){
        Optional<Student> result = studentService.findByCode(code);
        if (result.isPresent()){
            if (result.get().getPassword().equals(password)){
                return result.get();
            }
            else
                System.out.println("wrong password");
            return null;
        }
        System.out.println("student code does not exist");
        return null;
    }

    public Teacher teacherMenu(Integer code,String password){
        Optional<Teacher> result = teacherService.findByCode(code);
        if (result.isPresent()){
            if (result.get().getPassword().equals(password))
                return result.get();
            else
                System.out.println("wrong password");
            return null;
        }
        System.out.println("teacher code does not exist");
        return null;
    }


}
