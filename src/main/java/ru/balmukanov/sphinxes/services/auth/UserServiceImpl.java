package ru.balmukanov.sphinxes.services.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.balmukanov.sphinxes.entities.User;
import ru.balmukanov.sphinxes.exception.UserNotFoundException;
import ru.balmukanov.sphinxes.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new UserNotFoundException("User by username=%s not found".formatted(username)));
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return findByUsername(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
