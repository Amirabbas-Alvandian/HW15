package example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends Person{
    @Min(value = 100,message = "studentNumber should be between 100-199")
    @Max(value = 199,message = "studentNumber should be between 100-199")
    @Column(unique = true)
    private Integer studentNumber;
    private String major;
    @OneToMany(mappedBy = "student")
    private Set<StudentCourse> studentCourse = new HashSet<>();

    public Student(String firstName, String lastName, String password, Integer studentNumber, String major) {
        super(firstName, lastName, password);
        this.studentNumber = studentNumber;
        this.major = major;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentNumber=" + studentNumber +
                ", major='" + major + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
