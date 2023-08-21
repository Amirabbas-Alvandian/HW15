package example.entity;

import example.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public class Person extends BaseEntity {
    @Pattern(regexp = "^[a-zA-Z]*$",message = "first name should only contain alphabet")
    protected String firstName;
    @Pattern(regexp = "^[a-zA-Z]*$",message = "last name should only contain alphabet")
    protected String lastName;
    @Pattern(regexp = "^[0-9a-zA-Z]*$",message = "password should only contain alphabet and numbers")
    @Size(min = 4,max = 20,message = "password should have 4-20 characters")
    protected String password;

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
