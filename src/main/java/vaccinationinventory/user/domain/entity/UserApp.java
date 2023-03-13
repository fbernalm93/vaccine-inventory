package vaccinationinventory.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userApp")
public class UserApp {
    @Id
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
