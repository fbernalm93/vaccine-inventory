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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vaccinationinventory.person.application.PersonCrud;
import vaccinationinventory.person.domain.entity.Person;
import vaccinationinventory.utils.exceptions.InvalidIdException;

import javax.validation.Valid;

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
}
