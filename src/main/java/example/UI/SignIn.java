package example.UI;

import example.entity.Employee;
import example.entity.Student;
import example.entity.Teacher;
import example.service.EmployeeService;
import example.service.StudentService;
import example.service.TeacherService;

import java.util.Optional;

public class SignIn {
    private final EmployeeService employeeService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final EmployeeMenu gotoEmployeeMenu;
    private final StudentMenu studentMenu;

    private final TeacherMenu teacherMenu;

    public SignIn(EmployeeService employeeService, StudentService studentService,TeacherService teacherService,
                  EmployeeMenu gotoEmployeeMenu, StudentMenu studentMenu, TeacherMenu teacherMenu) {
        this.employeeService = employeeService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.gotoEmployeeMenu = gotoEmployeeMenu;
        this.studentMenu = studentMenu;
        this.teacherMenu = teacherMenu;
    }

    public void typeDetermine(Integer code, String password){
        if (code >= 0 && code < 100) {
            employeeMenu(code, password);
        } else if (code < 200 && code >= 100) {
            studentMenu(code, password);
        } else if (code < 300 && code >=200 ) {
            teacherMenu(code, password);
        } else {
            System.out.println("code out of any class range");
        }
    }

    public void employeeMenu(Integer code,String password){
        Optional<Employee> result = employeeService.findByCode(code);
        if (result.isPresent()){
            if (result.get().getPassword().equals(password))
                gotoEmployeeMenu.chooseClass(result.get());
            else
                System.out.println("wrong password");
            return;
        }
        System.out.println("employee code does not exist");
    }

    public void studentMenu(Integer code,String password){
        Optional<Student> result = studentService.findByCode(code);
        if (result.isPresent()){
            if (result.get().getPassword().equals(password)){
                studentMenu.choices(result.get());
            }
            else
                System.out.println("wrong password");
            return ;
        }
        System.out.println("student code does not exist");
    }

    public void teacherMenu(Integer code,String password){
        Optional<Teacher> result = teacherService.findByCode(code);
        if (result.isPresent()){
            if (result.get().getPassword().equals(password))
                teacherMenu.choices(result.get());
            else
                System.out.println("wrong password");
            return;
        }
        System.out.println("teacher code does not exist");
    }


}
