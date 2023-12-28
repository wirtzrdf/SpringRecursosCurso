package com.example.ejemplojwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ejemplojwt.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
  Usuario findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
