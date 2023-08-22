package example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
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
    @Column(unique = true)
    private Integer employeeCode;
    @Transient
    private final Double baseSalary = 1_000_000.0;

    public Employee(@Pattern(regexp = "^[a-zA-Z]*$", message = "first name should only contain alphabet") String firstName, @Pattern(regexp = "^[a-zA-Z]*$", message = "last name should only contain alphabet") String lastName, @Pattern(regexp = "^[0-9a-zA-Z]*$", message = "password should only contain alphabet and numbers") @Size(min = 4, max = 20, message = "password should have 4-20 characters") String password, Integer employeeCode) {
        super(firstName, lastName, password);
        this.employeeCode = employeeCode;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeCode=" + employeeCode +
                ", baseSalary=" + baseSalary +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
