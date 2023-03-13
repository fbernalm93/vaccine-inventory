package vaccinationinventory.person.application;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vaccinationinventory.person.domain.entity.Person;
import vaccinationinventory.person.domain.repository.PersonRepositoryInterface;
import vaccinationinventory.user.domain.entity.Role;
import vaccinationinventory.user.domain.entity.UserApp;
import vaccinationinventory.user.domain.repository.UserRepositoryInterface;
import vaccinationinventory.utils.Validations;
import vaccinationinventory.utils.exceptions.InvalidIdException;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonCrud implements PersonInterface{
    @Autowired
    private PersonRepositoryInterface personRepository;
    private UserRepositoryInterface userRepository;
    private Validations validationsUtils;
    private static Logger LOG = LoggerFactory.getLogger(PersonCrud.class);
    private BCryptPasswordEncoder encoder;
    @Override
    public void createEmployee(Person person) throws InvalidIdException {
        //Context validations
        if (!validationsUtils.isValidEcuadorianId(person.getId())) throw new InvalidIdException("Identification not valid - Ecuadorian Id");
        LOG.info("The Employee validations are OK");
        personRepository.save(person);
        UserApp user = new UserApp();
        user.setUsername(person.getId());
        //Set encrypt password
        user.setPassword(encoder.encode(person.getId()));
        user.setRole(Role.EMPLOYEE);
        userRepository.save(user);
    }

    @Override
    public void createAdministrator(Person person) {
        personRepository.save(person);
        UserApp user = new UserApp();
        user.setUsername(person.getId());
        //Set encrypt password
        user.setPassword(encoder.encode(person.getId()));
        user.setRole(Role.ADMINISTRATOR);
        userRepository.save(user);
    }

    @Override
    public void updatePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public List<Person> listAllEmployees() {
        return personRepository.findAll();
    }

    @Override
    public Person findEmployeeById(String id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePerson(String id) {
        Person person = personRepository.findById(id).orElse(null);
        personRepository.delete(person);
    }
}