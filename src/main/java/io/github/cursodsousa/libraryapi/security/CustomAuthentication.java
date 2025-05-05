package io.github.cursodsousa.libraryapi.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.github.cursodsousa.libraryapi.model.Usuario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {

    private final Usuario usuario;

    @Override
    public String getName() {
        return usuario.getLogin();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.usuario
            .getRoles()
            .stream()
            .map(SimpleGrantedAuthority::new) // passa o getRoles()
            .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCredentials'");
    }

    @Override
    public Object getDetails() {
        return usuario;
    }

    @Override
    public Object getPrincipal() {
        return usuario;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAuthenticated'");
    }

}
