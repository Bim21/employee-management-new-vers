package com.ncc.controller;

import com.ncc.dto.RoleDTO;
import com.ncc.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final IRoleService roleService;

    @PostMapping("add-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RoleDTO addRole(@RequestBody RoleDTO roleDTO){
        return roleService.addRole(roleDTO);
    }
}
