package com.dart.dao;

import com.dart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    //Add custom queries if any needed in future

    User findByEmail(String email);

    // Or you can use a custom JPQL query
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmailCustomQuery(@Param("email") String email);
}
