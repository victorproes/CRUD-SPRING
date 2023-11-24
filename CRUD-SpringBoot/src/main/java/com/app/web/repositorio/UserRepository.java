package com.app.web.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.web.entidad.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
