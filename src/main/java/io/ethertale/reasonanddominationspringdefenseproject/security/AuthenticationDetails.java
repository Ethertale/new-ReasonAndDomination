package io.ethertale.reasonanddominationspringdefenseproject.security;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@AllArgsConstructor
public class AuthenticationDetails implements UserDetails {

    private UUID id;
    private String email;
    private String password;
    private AccountRole role;
    private boolean isActive;
    private String profilePicture;

    // Authority - permission или роля
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // hasRole('ADMIN') -> "ROLE_" + ADMIN
        // hasAuthority('ADMIN') -> "ADMIN"
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.name());

        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
