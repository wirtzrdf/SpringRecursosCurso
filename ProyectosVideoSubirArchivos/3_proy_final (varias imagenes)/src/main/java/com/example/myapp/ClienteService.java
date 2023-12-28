package com.example.myapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImagenRepository imagenRepository;

    @Autowired
    public FileStorageService fileStorageService;

    public Cliente añadir(Cliente cliente, MultipartFile[] files) {
        Cliente clienteSaved = clienteRepository.save(cliente);
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String archivo = fileStorageService.store(file);
                    imagenRepository.save(new Imagen(null, archivo, clienteSaved));
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return clienteSaved;
    }

    @Transactional
    public Cliente editar(Cliente cliente, MultipartFile[] files) {
        borrarImagenesDisco(cliente.getId());
        imagenRepository.deleteByCliente(cliente);
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String archivo = fileStorageService.store(file);
                    imagenRepository.save(new Imagen(null, archivo, cliente));
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public void borrar(Long id) {
        this.borrarImagenesDisco(id);
        clienteRepository.deleteById(id);
    }

    public void borrarImagenesDisco(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            for (Imagen imagen : cliente.getImagenes()) {
                fileStorageService.delete(imagen.getNombre());
            }
        }
    }

}
