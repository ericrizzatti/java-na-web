package br.edu.biblioteca.controller;

import br.edu.biblioteca.model.*;
import br.edu.biblioteca.repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoRepository emprestimoRepository;
    private final LeitorRepository leitorRepository;
    private final LivroRepository livroRepository;

    public EmprestimoController(EmprestimoRepository emprestimoRepository, LeitorRepository leitorRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.leitorRepository = leitorRepository;
        this.livroRepository = livroRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", emprestimoRepository.findAll());
        return "emprestimos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("item", new Emprestimo());
        carregarListas(model);
        return "emprestimos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("item", emprestimoRepository.findById(id).orElseThrow());
        carregarListas(model);
        return "emprestimos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("item") Emprestimo item, RedirectAttributes ra) {
        try {
            emprestimoRepository.save(item);
            ra.addFlashAttribute("msg", "Registro salvo com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não foi possível salvar: dados inválidos ou duplicados (ex.: CPF ou ISBN já cadastrado).");
        }
        return "redirect:/emprestimos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            emprestimoRepository.deleteById(id);
            ra.addFlashAttribute("msg", "Registro excluído com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: o registro está em uso em outro cadastro.");
        }
        return "redirect:/emprestimos";
    }

    private void carregarListas(Model model) {
        model.addAttribute("leitores", leitorRepository.findAll());
        model.addAttribute("livros", livroRepository.findAll());
        model.addAttribute("statusList", StatusEmprestimo.values());
    }
}
