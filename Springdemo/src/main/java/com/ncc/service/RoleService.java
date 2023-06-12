package com.ncc.service;

import com.ncc.dto.RoleDTO;
import com.ncc.entity.Role;
import com.ncc.mapper.IRoleMapper;
import com.ncc.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final IRoleMapper roleMapper;

    private final IRoleRepository roleRepository;


    @Override
    public RoleDTO addRole(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        roleRepository.save(role);

        RoleDTO roleResponseDTO = roleMapper.toDTO(role);
        return roleResponseDTO;
    }
}
