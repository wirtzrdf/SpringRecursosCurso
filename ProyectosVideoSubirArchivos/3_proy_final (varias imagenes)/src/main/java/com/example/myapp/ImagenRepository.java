package com.example.myapp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {
    void deleteByCliente(Cliente cliente);
}
