package vaccinationinventory.user.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vaccinationinventory.person.domain.entity.Person;
import vaccinationinventory.security.TokenJWT;
import vaccinationinventory.user.application.UserService;
import vaccinationinventory.user.domain.entity.UserApp;

import javax.validation.Valid;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    @ApiOperation(value = "Create a new person with Employee Role in database and a new user with required fields " +
            "(id, " + "name, lastname,email),created user has username=person id and default password= person id," +
            "(Role administrador required)")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Login Ok"),
            @ApiResponse(code = 400, message = "Not Login")})
    public ResponseEntity<TokenJWT> userLogin(@Valid @RequestBody UserApp user, BindingResult result){
        return new ResponseEntity<TokenJWT>(userService.authenticateUSer(user), HttpStatus.OK);
    }
}
