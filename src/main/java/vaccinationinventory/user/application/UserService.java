package vaccinationinventory.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vaccinationinventory.security.JwtUtilService;
import vaccinationinventory.security.TokenJWT;
import vaccinationinventory.user.domain.entity.UserApp;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtilService jwt;
    @Autowired
    private UserDetailService detailService;

    public TokenJWT authenticateUSer(UserApp credentialsUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentialsUser.getUsername(), credentialsUser.getPassword()));
        final UserDetails userdetils = detailService.loadUserByUsername(credentialsUser.getUsername());

        final String tokenjwt = jwt.generateToken(userdetils);

        return new TokenJWT(tokenjwt);
    }
}
