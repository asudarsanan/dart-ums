package com.dart.dao;

import com.dart.model.Role;
import com.dart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    // Or you can use a custom JPQL query
    @Query("SELECT u FROM Role u WHERE u.roleName = :roleName")
    User findByRoleNameCustomQuery(@Param("roleName") String roleName);
}

