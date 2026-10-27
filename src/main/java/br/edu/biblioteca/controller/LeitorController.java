package br.edu.biblioteca.controller;

import br.edu.biblioteca.model.*;
import br.edu.biblioteca.repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/leitores")
public class LeitorController {

    private final LeitorRepository leitorRepository;

    public LeitorController(LeitorRepository leitorRepository) {
        this.leitorRepository = leitorRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", leitorRepository.findAll());
        return "leitores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("item", new Leitor());
        carregarListas(model);
        return "leitores/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("item", leitorRepository.findById(id).orElseThrow());
        carregarListas(model);
        return "leitores/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("item") Leitor item, RedirectAttributes ra) {
        try {
            leitorRepository.save(item);
            ra.addFlashAttribute("msg", "Registro salvo com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não foi possível salvar: dados inválidos ou duplicados (ex.: CPF ou ISBN já cadastrado).");
        }
        return "redirect:/leitores";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            leitorRepository.deleteById(id);
            ra.addFlashAttribute("msg", "Registro excluído com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: o registro está em uso em outro cadastro.");
        }
        return "redirect:/leitores";
    }

    private void carregarListas(Model model) {
    }
}
