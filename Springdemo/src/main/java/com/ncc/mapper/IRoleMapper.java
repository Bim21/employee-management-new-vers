package com.ncc.mapper;

import com.ncc.common.EntityMapper;
import com.ncc.dto.RoleDTO;
import com.ncc.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRoleMapper extends EntityMapper<RoleDTO, Role> {
}