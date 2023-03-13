package vaccinationinventory.person.domain.entity;


import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@Table(name = "person")

public class Person {
    @NotEmpty(message="Field is required")
    @Size(min=10,max = 10, message = "The length of identification should be 10")
    @Id
    @Column(name="id", length=10)
    @Pattern(regexp="^[0-9]*$",message = "The field Name should always contain digits")
    private String id;
    @NotEmpty(message="Field is required")
    @Column(name="name", length=100)
    @Pattern(regexp="^[A-Za-z]*$",message = "The field Name should always contain alphabetic characters")
    private String name;
    @NotEmpty(message="Field is required")
    @Column(name="lastname", length=100)
    @Pattern(regexp="^[A-Za-z]*$",message = "The field Last Name should always contain alphabetic characters")
    private String lastName;
    @NotEmpty(message="Field is required")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Column(name="email", length=75)
    private String email;
    @Column(name="birthdate")
    private Date birthdate;
    @Column(name="address", length=100)
    private String address;
    @Column(name="phonenumber",length=10)
    private String phonenumber;
    @Column(name="is_vaccinated")
    private Boolean isVaccinated;
}