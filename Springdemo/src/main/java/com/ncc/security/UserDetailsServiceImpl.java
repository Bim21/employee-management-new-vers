package com.ncc.security;

import com.ncc.entity.Employee;
import com.ncc.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IEmployeeRepository employeeRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findEmployeeByUserName(username);
        if(employee == null){
            throw new UsernameNotFoundException("User" + username + "was not found in the database");
        }
        return UserDetailsImpl.build(employee);
    }

}

// TODO: UserDetailsService
