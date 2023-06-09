package com.ncc.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ncc.entity.Employee;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class UserDetailsImpl implements UserDetails {
    private Integer id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    public Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetails build(Employee employee){
        List<GrantedAuthority> authorities = employee.getEmployeeRoles().stream()
                .map(employeeRole -> new SimpleGrantedAuthority(employeeRole.getRole().getRoleName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                employee.getId(),
                employee.getUserName(),
                employee.getEmail(),
                employee.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        UserDetailsImpl userDetails = (UserDetailsImpl) obj;
        return Objects.equals(id, userDetails.id);
    }
}
