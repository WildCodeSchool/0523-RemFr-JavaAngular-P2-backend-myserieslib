package com.templateproject.api.service;
import com.templateproject.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepositoryInjected) {
        this.userRepository = userRepositoryInjected;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return (UserDetails) this.userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Il n'y a aucun utilisateur avec cet email " + username));

    }
}
