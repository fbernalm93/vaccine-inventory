package vaccinationinventory.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userApp")
public class UserApp {
    @Id
    @NotEmpty(message="Field is required")
    @Column(name="username")
    private String username;
    @NotEmpty(message="Field is required")
    @Column(name="passsword")
    private String password;
    @NotEmpty(message="Field is required")
    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;
}
