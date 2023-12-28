package com.example.myapp;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ClienteRestController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    public FileStorageService fileStorageService;

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

    @PostMapping(value = "/cliente", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendElement(@RequestPart("data") Cliente cliente,
            @RequestPart("file") MultipartFile file) {
        Cliente clienteSaved = clienteService.añadir(cliente, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSaved);
    }

    @PutMapping(value = "/cliente/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editElement(@RequestPart("data") Cliente cliente,
            @RequestPart("file") MultipartFile file, @PathVariable Long id) {
        if (id != cliente.getId())
            return ResponseEntity.badRequest().build();
        Cliente clienteSaved = clienteService.editar(cliente, file);
        return ResponseEntity.ok().body(clienteSaved);
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<?> deleteElement(@PathVariable long id) {
        if (clienteService.obtenerPorId(id) == null)
            return ResponseEntity.notFound().build();
        clienteService.borrar(id);
        return ResponseEntity.noContent().build();
    }

     @GetMapping(value = "/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request) {
        Resource file = fileStorageService.loadAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.err.println("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

}
