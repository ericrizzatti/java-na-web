package br.edu.biblioteca.controller;

import br.edu.biblioteca.model.*;
import br.edu.biblioteca.repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroController(LivroRepository livroRepository, AutorRepository autorRepository, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", livroRepository.findAll());
        return "livros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("item", new Livro());
        carregarListas(model);
        return "livros/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("item", livroRepository.findById(id).orElseThrow());
        carregarListas(model);
        return "livros/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("item") Livro item, RedirectAttributes ra) {
        try {
            livroRepository.save(item);
            ra.addFlashAttribute("msg", "Registro salvo com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não foi possível salvar: dados inválidos ou duplicados (ex.: CPF ou ISBN já cadastrado).");
        }
        return "redirect:/livros";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            livroRepository.deleteById(id);
            ra.addFlashAttribute("msg", "Registro excluído com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: o registro está em uso em outro cadastro.");
        }
        return "redirect:/livros";
    }

    private void carregarListas(Model model) {
        model.addAttribute("autores", autorRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
    }
}
