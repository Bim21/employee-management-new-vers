package com.ncc.repository;

import com.ncc.entity.ERole;
import com.ncc.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r " +
            "WHERE r.id IN (:roleIds)")
    List<Role> getSetRoleByRoleId(List<Integer> roleIds);

    Optional<Role> findByRoleName(ERole roleName);
}
