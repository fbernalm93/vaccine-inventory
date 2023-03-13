package vaccinationinventory.person.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vaccinationinventory.person.domain.entity.Person;

import java.util.List;

@Repository
public interface PersonRepositoryInterface extends JpaRepository<Person, String> {
    List<Person> findAll();
}
