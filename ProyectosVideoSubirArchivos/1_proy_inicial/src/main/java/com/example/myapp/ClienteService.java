package com.example.myapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repositorio;

    public Cliente añadir(Cliente cliente) {
        return repositorio.save(cliente);
    }

    public List<Cliente> obtenerTodos() {
        return repositorio.findAll();
    }

    public Cliente obtenerPorId(long id) {
        return repositorio.findById(id).orElse(null);
    }

    public Cliente editar(Cliente cliente) {
        return repositorio.save(cliente);
    }

    public void borrar(Long id) {
        repositorio.deleteById(id);
    }

}
