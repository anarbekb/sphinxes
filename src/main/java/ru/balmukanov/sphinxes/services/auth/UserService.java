package ru.balmukanov.sphinxes.services.auth;

import ru.balmukanov.sphinxes.entities.User;

public interface UserService {
    User findByUsername(String username);
}
