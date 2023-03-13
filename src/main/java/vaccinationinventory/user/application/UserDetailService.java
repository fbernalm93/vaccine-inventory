package vaccinationinventory.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vaccinationinventory.user.domain.entity.UserApp;
import vaccinationinventory.user.domain.repository.UserRepositoryInterface;

import java.util.ArrayList;
import java.util.Collection;
@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepositoryInterface userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp user = userRepository.findById(username).get();
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado!!!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        UserDetails userdet = new User(user.getUsername(), user.getPassword(), authorities);
        return userdet;
    }
}
