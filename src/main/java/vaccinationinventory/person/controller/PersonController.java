//@Path("/product")
//@Produces(MediaType.APPLICATION_JSON)

package vaccinationinventory.person.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vaccinationinventory.person.application.PersonCrud;
import vaccinationinventory.person.domain.entity.Person;
import vaccinationinventory.utils.exceptions.InvalidIdException;

import javax.validation.Valid;

import java.util.List;

import static vaccinationinventory.utils.Validations.formatMessage;

@RestController
@RequestMapping("/person")
public class PersonController{
    @Autowired
    private PersonCrud personService;
    private static Logger LOG = LoggerFactory.getLogger(PersonController.class);
    @PostMapping("/newemployee")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @ApiOperation(value = "Create a new person with Employee Role in database and a new user with required fields " +
            "(id, " + "name, lastname,email),created user has username=person id and default password= person id," +
            "(Role administrador required)")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Person created"),
            @ApiResponse(code = 400, message = "Validation error in object")})
    public ResponseEntity<Boolean> newEmployee(@Valid @RequestBody Person person, BindingResult result) {
        LOG.info("Initializing - New employee create process");
        ResponseEntity response;
        if(result.hasErrors()){
            response = new ResponseEntity(formatMessage(result),HttpStatus.BAD_REQUEST);
        }else{
            try {
                personService.createEmployee(person);
                response = new ResponseEntity("New Employee created!", HttpStatus.CREATED);
            } catch (InvalidIdException e) {
                response = new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
            }catch (Exception er){
                response = new ResponseEntity(er.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return response;
    }

    @PostMapping("/updateemployee")
    @ApiOperation(value = "Update person in database. You can change any field like as" +
            "(address, birthdate, phonenumber, )")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Person updated"),
            @ApiResponse(code = 400, message = "Validation error in object")})
    public ResponseEntity<Boolean> updateEmployee(@Valid @RequestBody Person person) {
        LOG.info("Initializing - Update employee create process");
        Person searchPerson = personService.findEmployeeById(person.getId());
        if (searchPerson != null){
            searchPerson.setAddress(person.getAddress());
            searchPerson.setBirthdate(person.getBirthdate());
            searchPerson.setPhonenumber(person.getPhonenumber());
            personService.updatePerson(searchPerson);
            return new ResponseEntity("Employee updated!", HttpStatus.OK);
        }else{
            return new ResponseEntity("Employee not found!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/deleteemployee")
    @ApiOperation(value = "Delete person with Employee Role in database by Id Person )" +
            "(Role administrador required)")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Person deleted"),
            @ApiResponse(code = 400, message = "Validation error in object")})
    public ResponseEntity<Boolean> deleteEmployee(@Valid @RequestBody Person person) {
        LOG.info("Initializing - Delete employee create process");
        Person searchPerson  = personService.findEmployeeById(person.getId());
        if (searchPerson!=null){
            personService.deletePerson(person.getId());
            return new ResponseEntity("Employee deleted!", HttpStatus.OK);
        }else{
            return new ResponseEntity("Id not found", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listall")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @ApiOperation(value = "Get all persons on database - Role administrador required")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "List of persons") })
    public ResponseEntity<List<Person>> listAll() {
        return new ResponseEntity<>(personService.listAllEmployees(), HttpStatus.OK);
    }

}
