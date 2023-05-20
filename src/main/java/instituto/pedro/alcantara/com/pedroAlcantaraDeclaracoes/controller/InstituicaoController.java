package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.InstituicaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.InstituicaoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/instituicoes")
@RequiredArgsConstructor

public class InstituicaoController {
    //Value to set pagination quantity
    private static final int PAGE_SIZE = 10;

    private final InstituicaoService instituicaoService;
    // private final EstudanteService estudanteService;

    @GetMapping(value = "/list")
    public ModelAndView index(ModelAndView model, Integer page) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, PAGE_SIZE);
        Page<Instituicao> entityPage = instituicaoService.getAll(pageable);

        model.setViewName("instituicoes/list");
        model.addObject("menu", "instituicoes");
        model.addObject("instituicoes", entityPage.getContent());
        model.addObject("currentPage", entityPage.getNumber());
        model.addObject("totalPages", entityPage.getTotalPages());
        model.addObject("pagePath", "/instituicoes/list");

        return model;
    }

    @GetMapping(value = "/create")
    public ModelAndView create(InstituicaoDTO instituicao, ModelAndView model) {

        model.setViewName("instituicoes/form");
        model.addObject("instituicao", instituicao);
        model.addObject("method", "POST");
        return model;
    }

    @GetMapping(value = "/create/{id}")
    public ModelAndView updateInstituicaoForm(@PathVariable(value = "id") Integer id, ModelAndView model) {
        try {
            Instituicao instituicao = instituicaoService.getById(id);
            model.setViewName("instituicoes/form");
            model.addObject("instituicao", this.converter(instituicao));
            model.addObject("method", "PUT");
            model.addObject("periodos", instituicao.getPeriodos());
        } catch (Exception e) {
            model.setViewName("instituicoes/form");
            model.addObject("exception", e.getMessage());
            model.addObject("instituicao", new Instituicao());
            model.addObject("method", "POST");
        }
        return model;
    }

    public List<InstituicaoDTO> getAll() {

        return this.instituicaoService.getAll()
                .stream().map(i -> {
                    return this.converter(i);
                }).collect(Collectors.toList());
    }

    @PostMapping(value = "/create")
    public ModelAndView salvar(@Valid @ModelAttribute("instituicao") InstituicaoDTO i, BindingResult validation,
            ModelAndView model, RedirectAttributes ra) {

        if (validation.hasErrors()) {
            model.addObject("instituicao", i);
            model.addObject("method", "POST");
            model.addObject("hasErrors", true);
            model.setViewName("instituicoes/form");
            return model;
        }

        try {
            this.instituicaoService.save(i);
        } catch (Exception e) {
            model.addObject("instituicao", i);
            model.addObject("exception", e.getMessage());
            model.addObject("method", "POST");
            model.setViewName("instituicoes/form");
            return model;
        }

        ra.addFlashAttribute("mensagem", "A instituição foi Cadastrada com Sucesso!");
        ra.addFlashAttribute("success", true);
        model.setViewName("redirect:list");
        return model;
    }

    @PutMapping(value = "/create")
    public ModelAndView updateInstituicao(@Valid @ModelAttribute("instituicao") InstituicaoDTO i,
            BindingResult validation,
            ModelAndView model, RedirectAttributes ra) {

        if (validation.hasErrors()) {
            model.addObject("instituicao", i);
            model.addObject("method", "PUT");
            model.addObject("hasErrors", true);
            model.setViewName("instituicoes/form");
            return model;
        }

        try {
            this.instituicaoService.update(i);
        } catch (Exception e) {
            model.addObject("instituicao", i);
            model.addObject("exception", e.getMessage());
            model.addObject("method", "POST");
            model.setViewName("instituicoes/form");
            return model;
        }

        ra.addFlashAttribute("mensagem", "A instituição foi Cadastrada com Sucesso!");
        ra.addFlashAttribute("success", true);
        model.setViewName("redirect:list");
        return model;
    }

    private List<EstudanteDTO> converterEstudanteDTO(List<Estudante> e) {
        return e.stream().map(estudante -> {
            return EstudanteDTO
                    .builder()
                    .matricula(estudante.getMatricula())
                    .nome(estudante.getNome())
                    .instituicaoAtual(estudante.getInstituicaoAtual().getId())
                    .build();
        }).collect(Collectors.toList());

    }

    private InstituicaoDTO converter(Instituicao i) {

        Integer periodo = i.getPeriodoAtual() != null ? i.getPeriodoAtual().getId() : null;

        return InstituicaoDTO.builder()
                .id(i.getId())
                .nome(i.getNome())
                .sigla(i.getSigla())
                .fone(i.getFone())
                .alunos(this.converterEstudanteDTO(i.getAlunos()))
                .periodoAtual(periodo)
                .build();

    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteInstituicao(@PathVariable(name = "id") Integer id, ModelAndView model,
            RedirectAttributes ra) {
        try {
            this.instituicaoService.deleteInstituicao(id);
        } catch (Exception e) {
            ra.addFlashAttribute("mensagem", e.getMessage());
            model.setViewName("redirect:/instituicoes/list");
            return model;
        }
        model.setViewName("redirect:/instituicoes/list");
        ra.addFlashAttribute("mensagem", "Instituicao removido com Sucesso!");
        return model;
    }

}
// a
