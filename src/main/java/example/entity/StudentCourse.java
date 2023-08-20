package example.entity;

import com.sun.istack.Nullable;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "scores_history")
public class StudentCourse  implements Serializable{

    @EmbeddedId
    private StudentCourseId id;

    @ManyToOne
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    private Course course;
    @DecimalMax(value = "20.0",message = "score cant be more than 20.0")
    @Min(value = 0,message = "Score cant be less than zero")
    @Nullable
    private Double score;
    @Digits(integer = 4,fraction = 0,message = "semester should only be 4 numbers")
    @Column(updatable = false, insertable=false )
    private Integer semester;


    public StudentCourse(Student student, Course course, Double score, Integer semester) {
        this.id = new StudentCourseId(student.getId(), course.getId(), semester);
        this.student = student;
        this.course = course;
        this.score = score;
        this.semester = semester;
    }

    public StudentCourse(Student student, Course course, Integer semester) {
        this.id = new StudentCourseId(student.getId(), course.getId(), semester);
        this.student = student;
        this.course = course;
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "StudentCourse{" +
                "student=" + student +
                ", course=" + course +
                ", score=" + score +
                ", semester=" + semester +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourse that = (StudentCourse) o;
        return Objects.equals(student, that.student) && Objects.equals(course, that.course) && Objects.equals(score, that.score) && Objects.equals(semester, that.semester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course, score, semester);
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @Embeddable
    public static class StudentCourseId implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private Long studentId;
        private Long courseId;
        private Integer semester;


        public StudentCourseId(Long studentId, Long courseId,Integer semester) {
            super();
            this.studentId = studentId;
            this.courseId = courseId;
            this.semester = semester;
        }


    }
}
