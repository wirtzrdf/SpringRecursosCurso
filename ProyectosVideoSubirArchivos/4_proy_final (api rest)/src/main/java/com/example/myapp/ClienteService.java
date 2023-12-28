package com.example.myapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repositorio;

    @Autowired
    public FileStorageService fileStorageService;

    public Cliente añadir(Cliente cliente, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String nombreImagen = fileStorageService.store(file);
                cliente.setImagen(nombreImagen);
            } catch (Exception e) {
                return null;
            }
        }
        return repositorio.save(cliente);
    }

    public Cliente editar(Cliente cliente, MultipartFile file) {
        // borrar imagen anterior
        this.borrarImagen(cliente.getId());

        if (!file.isEmpty()) {
            try {
                String nombreImagen = fileStorageService.store(file);
                cliente.setImagen(nombreImagen);
            } catch (Exception e) {
                return null;
            }
        }
        return repositorio.save(cliente);
    }

    public List<Cliente> obtenerTodos() {
        return repositorio.findAll();
    }

    public Cliente obtenerPorId(long id) {
        return repositorio.findById(id).orElse(null);
    }

    public void borrar(Long id) {
        this.borrarImagen(id);
        repositorio.deleteById(id);
    }

    public void borrarImagen(Long id) {
        Cliente cliente = repositorio.findById(id).orElse(null);
        if (cliente != null) {
            fileStorageService.delete(cliente.getImagen());
        }
    }

}
