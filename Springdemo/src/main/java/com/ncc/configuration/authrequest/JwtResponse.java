package com.ncc.configuration.authrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String jwt;
    private Integer id;
    private String username;
    private String email;
    private List<String> roles;
}
