package vaccinationinventory.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vaccinationinventory.user.domain.entity.UserApp;

public interface UserRepositoryInterface extends JpaRepository<UserApp,String> { }