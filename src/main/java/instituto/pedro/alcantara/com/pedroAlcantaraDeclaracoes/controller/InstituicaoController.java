package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    private final InstituicaoService instituicaoService;
    // private final EstudanteService estudanteService;

    @GetMapping(value = "/list")
    public ModelAndView index(ModelAndView model) {

        model.setViewName("instituicoes/list");
        model.addObject("menu", "instituicoes");
        model.addObject("instituicoes", instituicaoService.getAll());
        return model;
    }

    @GetMapping(value = "/create")
    public ModelAndView create(InstituicaoDTO instituicao, ModelAndView model) {

        model.setViewName("instituicoes/form");
        model.addObject("instituicao", instituicao);
        model.addObject("method", "POST");
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
            model.setViewName("instituicoes/form");
            return model;
        }

        try {
            this.instituicaoService.save(i);
        } catch (Exception e) {
            model.addObject("instituicao", i);
            model.addObject("exception", e.getMessage());
            model.setViewName("instituicoes/form");
            return model;
        }

        ra.addFlashAttribute("mensagem", "A instituição foi Cadastrada com Sucesso!");
        model.addObject("instituicoes", instituicaoService.getAll());
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

        return InstituicaoDTO.builder()
                .nome(i.getNome())
                .sigla(i.getSigla())
                .fone(i.getFone())
                .alunos(this.converterEstudanteDTO(i.getAlunos()))
                .build();

    }

}
// a