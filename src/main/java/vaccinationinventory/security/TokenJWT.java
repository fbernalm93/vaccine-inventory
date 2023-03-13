package vaccinationinventory.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenJWT {
	private final String tokenJwt;

}
