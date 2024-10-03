package group.microserviceproduct.common.security;

import group.microserviceproduct.common.entity.user.User;
import group.microserviceproduct.common.entity.user.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    public UserDetails getUser(Long userId, String username, String email, UserRoleEnum role) throws UsernameNotFoundException {
        User user = new User(userId, username, email, role);
        return new UserDetailsImpl(user);
    }

}
