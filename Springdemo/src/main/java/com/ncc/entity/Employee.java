package com.ncc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "username", length = 50, unique = true)
    private String userName;

    @Column(name = "password", length = 800)
    private String password;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "check_in_code", unique = true)
    private Integer employeeCode;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "role")
    private ERole role;
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<EmployeeRole> employeeRoles;
}
