package com.ncc.service;

import com.ncc.configuration.authrequest.JwtResponse;
import com.ncc.configuration.authrequest.LoginRequest;
import com.ncc.configuration.authrequest.SignUpRequest;
import com.ncc.entity.ERole;
import com.ncc.entity.Employee;
import com.ncc.entity.EmployeeRole;
import com.ncc.entity.Role;
import com.ncc.repository.IEmployeeRepository;
import com.ncc.repository.IEmployeeRoleRepository;
import com.ncc.repository.IRoleRepository;
import com.ncc.security.UserDetailsImpl;
import com.ncc.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final IEmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IEmployeeRoleRepository employeeRoleRepository;
    private final IRoleRepository roleRepository;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if (employeeRepository.existsEmployeeByUserName(signUpRequest.getUserName())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        if (employeeRepository.existsEmployeeByEmail(signUpRequest.getEmail())) {
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

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(ERole.EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
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
        for (Role role : roles) {
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployee(employeeRepository.getById(employee.getId()));
            employeeRole.setRole(role);
            employeeRoles.add(employeeRole);
        }
        employee.setEmployeeRoles(employeeRoles);
        employeeRoles = employeeRoleRepository.saveAll(employeeRoles);

        return ResponseEntity.ok("User registered successfully!");
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
}
