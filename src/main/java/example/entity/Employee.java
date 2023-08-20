package example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@EqualsAndHashCode(callSuper = true)
public class Employee extends Person{
    @Min(value = 0,message = "employeeCode should be between 0-99")
    @Max(value = 99,message = "employeeCode should be between 0-99")
    private Integer employeeCode;
    @Transient
    private final Double baseSalary = 1_000_000.0;

    public Employee(String firstName, String lastName, String password) {
        super(firstName, lastName,password);
    }

    @Override
    public String toString() {
        return "Employee{" +
                ", password='" + password + '\'' +
                ", baseSalary=" + baseSalary +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
