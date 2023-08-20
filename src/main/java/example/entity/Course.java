package example.entity;

import example.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueColumns",columnNames = {"name","teacher_id"})})
//@EqualsAndHashCode(callSuper = true)
public class Course extends BaseEntity {
    @Column(name = "name")
    //@Enumerated(EnumType.STRING)
    private String courseName;
    @Min(value = 1,message = "Unit at least should be 1")
    @Max(value = 4,message = "Unit cant be more 4")
    private int unit;
    @OneToMany(mappedBy = "course")
    private Set<StudentCourse> studentCourse = new HashSet<>();
    @ManyToOne
    private Teacher teacher;

    public Course( Courses courseName, Teacher teacher) {
        this.courseName = courseName.toString();
        this.teacher = teacher;
        unit = courseName.getUnit();
    }

    public Course(String courseName, int unit, Teacher teacher) {
        this.courseName = courseName;
        this.unit = unit;
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName=" + courseName +
                ", unit=" + unit +
                ", id=" + id +
                '}';
    }
}
