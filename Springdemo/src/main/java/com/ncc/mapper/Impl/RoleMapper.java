package com.ncc.mapper.Impl;

import com.ncc.dto.RoleDTO;
import com.ncc.entity.Role;
import com.ncc.mapper.IRoleMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleMapper implements IRoleMapper {
    @Override
    public Role toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        Role role = new Role();

        role.setId(dto.getRoleId());
        role.setRoleName(dto.getRoleName());

        return role;
    }

    @Override
    public RoleDTO toDTO(Role entity) {
        if (entity == null) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setRoleId(entity.getId());
        roleDTO.setRoleName(entity.getRoleName());

        return roleDTO;
    }

    @Override
    public List<Role> toEntityList(List<RoleDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Role> list = new ArrayList<Role>(dtoList.size());
        for (RoleDTO roleDTO : dtoList) {
            list.add(toEntity(roleDTO));
        }

        return list;
    }

    @Override
    public List<RoleDTO> toDTOList(List<Role> entityList) {
        if (entityList == null) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>(entityList.size());
        for (Role role : entityList) {
            list.add(toDTO(role));
        }

        return list;
    }
}

