package com.ncc.service;

import com.ncc.configuration.authrequest.LoginRequest;
import com.ncc.configuration.authrequest.SignUpRequest;
import com.ncc.entity.ERole;
import com.ncc.entity.Employee;
import com.ncc.entity.EmployeeRole;
import com.ncc.entity.Role;
import com.ncc.repository.IEmployeeRepository;
import com.ncc.repository.RoleRepository;
import com.ncc.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    private final IEmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if(employeeRepository.existsEmployeeByUserName(signUpRequest.getUserName())){
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        if(employeeRepository.existsEmployeeByEmail(signUpRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        Employee employee = new Employee();
        employee.setUserName(signUpRequest.getUserName());
        employee.setPassword(passwordEncoder.encode(signUpRequest.getPassWord()));
        employee.setEmail(signUpRequest.getEmail());
        employee = employeeRepository.save(employee);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){
            Role userRole = roleRepository.findByRoleName(ERole.EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        }
        else{
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);

                        break;
                    default:
                        Role employeeRole = roleRepository.findByRoleName(ERole.EMPLOYEE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(employeeRole);
                }
            });
        }
        List<EmployeeRole> employeeRoles = new ArrayList<>();
        for (Role role : roles){
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployee(employeeRepository.getById(employee.getId()));
            employeeRole.setRole(role);
            employeeRoles.add(employeeRole);
        }
        employee.setEmployeeRoles(employeeRoles);
        employeeRoles = employeeRoleRepo.saveAll(employeeRoles);
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        return null;
    }
}
