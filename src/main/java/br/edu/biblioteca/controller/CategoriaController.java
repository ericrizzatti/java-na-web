package br.edu.biblioteca.controller;

import br.edu.biblioteca.model.*;
import br.edu.biblioteca.repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", categoriaRepository.findAll());
        return "categorias/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("item", new Categoria());
        carregarListas(model);
        return "categorias/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("item", categoriaRepository.findById(id).orElseThrow());
        carregarListas(model);
        return "categorias/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("item") Categoria item, RedirectAttributes ra) {
        try {
            categoriaRepository.save(item);
            ra.addFlashAttribute("msg", "Registro salvo com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não foi possível salvar: dados inválidos ou duplicados (ex.: CPF ou ISBN já cadastrado).");
        }
        return "redirect:/categorias";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            categoriaRepository.deleteById(id);
            ra.addFlashAttribute("msg", "Registro excluído com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: o registro está em uso em outro cadastro.");
        }
        return "redirect:/categorias";
    }

    private void carregarListas(Model model) {
    }
}
