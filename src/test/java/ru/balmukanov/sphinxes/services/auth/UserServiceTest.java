package ru.balmukanov.sphinxes.services.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.balmukanov.sphinxes.entities.User;
import ru.balmukanov.sphinxes.exception.UserNotFoundException;
import ru.balmukanov.sphinxes.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    @Test
    void findByUsername_happyPath() {
        when(userRepository.findByUsernameWithRoles(anyString())).thenReturn(Optional.of(createUser()));
        var userService = new UserServiceImpl(userRepository);

        assertDoesNotThrow(() -> userService.findByUsername("test"));
    }

    @Test
    void findByUsername_notFound() {
        when(userRepository.findByUsernameWithRoles(anyString())).thenReturn(Optional.empty());
        var userService = new UserServiceImpl(userRepository);

        assertThrows(UserNotFoundException.class, () -> userService.findByUsername("test"));
    }

    @Test
    void loadUserByUsername_happyPath() {
        when(userRepository.findByUsernameWithRoles(anyString())).thenReturn(Optional.of(createUser()));
        var userService = new UserServiceImpl(userRepository);

        assertDoesNotThrow(() -> userService.loadUserByUsername("test"));
    }

    @Test
    void loadUserByUsername_notFound() {
        when(userRepository.findByUsernameWithRoles(anyString())).thenReturn(Optional.empty());
        var userService = new UserServiceImpl(userRepository);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("test"));
    }

    private User createUser() {
        var user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("test");

        return user;
    }
}
