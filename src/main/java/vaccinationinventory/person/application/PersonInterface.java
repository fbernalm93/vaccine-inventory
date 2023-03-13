package vaccinationinventory.person.application;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vaccinationinventory.person.domain.entity.Person;
import vaccinationinventory.utils.exceptions.InvalidIdException;

import java.util.List;
@Service
public interface PersonInterface {
    public void createEmployee(Person person) throws InvalidIdException;
    public void createAdministrator(Person person);
    public void updatePerson(Person person);
    public List<Person> listAllEmployees();
    public Person findEmployeeById(String id);
    public void deletePerson(String id);
}
