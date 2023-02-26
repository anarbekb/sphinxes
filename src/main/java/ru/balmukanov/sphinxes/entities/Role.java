package ru.balmukanov.sphinxes.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Role implements GrantedAuthority, Serializable {
    private long id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
