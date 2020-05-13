package com.navtech.repository.jwt;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.navtech.model.authenticate.Role;
import com.navtech.model.authenticate.RoleName;
 
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> 
{
    Optional<Role> findByName(RoleName roleName);
}