package com.example.myapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClienteRestController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> getAll() {
        return clienteService.obtenerTodos();
    }

    @GetMapping("/cliente/id")
    public ResponseEntity<?> getOneElement(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(cliente);
    }


    @PostMapping("/cliente")
    public ResponseEntity<?> sendElement(@RequestBody Cliente cliente) {
        Cliente clienteSaved = clienteService.añadir(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSaved);
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<?> updateElement(@RequestBody Cliente cliente, @PathVariable Long id) {
        if (id != cliente.getId())
            return ResponseEntity.badRequest().build();
        Cliente clienteSaved = clienteService.editar(cliente);
        return ResponseEntity.ok().body(clienteSaved);
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<?> deleteElement(@PathVariable long id) {
        if (clienteService.obtenerPorId(id) == null)
            return ResponseEntity.notFound().build();
        clienteService.borrar(id);
        return ResponseEntity.noContent().build();
    }
}
