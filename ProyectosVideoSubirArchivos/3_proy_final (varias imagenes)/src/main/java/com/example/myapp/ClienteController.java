package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    public FileStorageService fileStorageService;

    @GetMapping("/")
    public String showList(Model model) {
        model.addAttribute("listaClientes", clienteService.obtenerTodos());
        return "listView";
    }

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("clienteForm", new Cliente());
        return "newFormView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(Cliente clienteForm,
            @RequestParam MultipartFile[] files) {
        clienteService.añadir(clienteForm, files);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente != null) {
            model.addAttribute("clienteForm", cliente);
            return "editFormView";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/edit/submit")
    public String showEditSubmit(Cliente clienteForm, @RequestParam MultipartFile[] files) {
        clienteService.editar(clienteForm, files);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id) {
        clienteService.borrar(id);
        return "redirect:/";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileStorageService.loadAsResource(filename);
        return ResponseEntity.ok().body(file);
    }

}
