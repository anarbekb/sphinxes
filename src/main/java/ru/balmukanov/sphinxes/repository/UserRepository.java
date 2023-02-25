package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.balmukanov.sphinxes.entities.User;

import java.util.Optional;

@Mapper
public interface UserRepository {
    Optional<User> findByUsernameWithRoles(String username);
}
