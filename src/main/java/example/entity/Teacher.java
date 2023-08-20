package example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
