package vaccinationinventory.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Configuration
public class Validations {
    /**
     * Method to validate ecuadorian id
     * @param identification
     * @return
     * boolen value from the validation
     */
    public boolean isValidEcuadorianId(String identification){
        // Regular expression to check that the ID has the correct format
        String regex = "^(?=.{10}$)\\d{10}$";

        //TODO probar con mas o menos de 10 digitos

        // Check that the Ecuadorian identification complies with the format (digits and size)
        if (!identification.matches(regex)) {
            return false;
        }
        // Extract the Ecuadorian identification number and the verification digit
        int checkDigit = Integer.parseInt(identification.substring(9, 10));
        // Calculate expected digit to validate ecuadorian id
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Integer.parseInt(identification.substring(i, i+1));
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        int expectedDigit = 10 - (sum % 10);
        if (expectedDigit == 10) {
            expectedDigit = 0;
        }

        // Check that the check digit is valid - Validate Ecuadorian Id
        return checkDigit == expectedDigit;
    }

    public boolean isValidEmail(String email){
        //Validate with regular expressions
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();

    }

    public boolean isNullValue (String value){
        return value == null || value.isEmpty();
    }

    public  boolean isValidAlphabeticString (String input){
        if(isNullValue(input)) return false;
        // Defines the regular expression to search for non-alphabetic characters
        String regex = "[^a-zA-Z]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        // If the regular expression is not found, the input is valid
        return (!matcher.find());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}