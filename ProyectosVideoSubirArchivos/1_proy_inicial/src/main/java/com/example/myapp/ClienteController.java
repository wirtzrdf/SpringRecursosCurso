package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

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
    public String showNewSubmit(Cliente clienteForm) {
        clienteService.añadir(clienteForm);
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
    public String showEditSubmit(Cliente clienteForm) {
        clienteService.editar(clienteForm);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id) {
        clienteService.borrar(id);
        return "redirect:/";
    }
}
