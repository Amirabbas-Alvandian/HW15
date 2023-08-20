package example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@EqualsAndHashCode(callSuper = true)
public class Teacher extends Person{
    @Enumerated(EnumType.STRING)
    private TeacherRank rank;
    @Column(unique = true)
    @Min(200)
    @Max(299)
    private Integer teacherCode;
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    public Teacher(@Pattern(regexp = "^[a-zA-Z]*$", message = "first name should only contain alphabet") String firstName,
                   @Pattern(regexp = "^[a-zA-Z]*$", message = "last name should only contain alphabet") String lastName,
                   @Size(min = 4, max = 20, message = "password should have 4-20 characters") String password, TeacherRank rank, Integer teacherCode) {
        super(firstName, lastName, password);
        this.rank = rank;
        this.teacherCode = teacherCode;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "rank=" + rank +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
