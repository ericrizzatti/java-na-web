package br.edu.biblioteca.controller;

import br.edu.biblioteca.model.*;
import br.edu.biblioteca.repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final AutorRepository autorRepository;

    public AutorController(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", autorRepository.findAll());
        return "autores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("item", new Autor());
        carregarListas(model);
        return "autores/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("item", autorRepository.findById(id).orElseThrow());
        carregarListas(model);
        return "autores/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("item") Autor item, RedirectAttributes ra) {
        try {
            autorRepository.save(item);
            ra.addFlashAttribute("msg", "Registro salvo com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não foi possível salvar: dados inválidos ou duplicados (ex.: CPF ou ISBN já cadastrado).");
        }
        return "redirect:/autores";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            autorRepository.deleteById(id);
            ra.addFlashAttribute("msg", "Registro excluído com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: o registro está em uso em outro cadastro.");
        }
        return "redirect:/autores";
    }

    private void carregarListas(Model model) {
    }
}
