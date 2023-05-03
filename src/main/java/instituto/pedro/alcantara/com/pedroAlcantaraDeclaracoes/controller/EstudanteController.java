package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.DeclaracaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.estudante.EstudanteNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.EstudanteService;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.InstituicaoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/estudantes")
@RequiredArgsConstructor

public class EstudanteController {

    private final EstudanteService estudanteService;
    private final InstituicaoService instituicaoService;

    @GetMapping(value = "/list")
    public ModelAndView list(ModelAndView model) {
        model.setViewName("estudantes/list");
        model.addObject("menu", "estudantes");
        model.addObject("estudantes", estudanteService.getAll());

        return model;
    }

    @GetMapping(value = "/list/instituicao/{id}")
    public ModelAndView listByInstituicao(@PathVariable(value = "id") Integer id, ModelAndView model) {
        model.setViewName("estudantes/list");
        model.addObject("menu", "estudantes");
        model.addObject("estudantes", instituicaoService.getAllEstudantesById(id));

        return model;
    }

    @GetMapping(value = "/create/{id}")
    public ModelAndView updateEstudanteForm(@PathVariable(value = "id") Integer id, ModelAndView model) {
        Optional<Estudante> estudante = estudanteService.getById(id);
        if (estudante.isPresent()) {
            model.setViewName("estudantes/form");
            model.addObject("method", "PUT");
            model.addObject("estudante", this.converterEstudanteDTO(estudante.get()));
            model.addObject("instituicoes", instituicaoService.getAll());
        } else {
            model.setViewName("estudantes/form");
            model.addObject("estudante", this.converterEstudanteDTO(new Estudante()));
            model.addObject("method", "POST");
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("estudantes", estudanteService.getAll());
        }

        return model;
    }

    // LocalDate dataInicio = LocalDate.parse(p.getDataInicio(),
    // DateTimeFormatter.ISO_LOCAL_DATE);
    private DeclaracaoDTO converterDeclaracaoDTO(Declaracao declaracao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

        return DeclaracaoDTO
                .builder()
                .id(declaracao.getId())
                .observacao(declaracao.getObservacao())
                .dataRecebimento(declaracao.getDataRecebimento().format(formatter))
                .estudante(declaracao.getEstudante().getMatricula())
                .build();
    }

    private EstudanteDTO converterEstudanteDTO(Estudante i) {
        Integer instituicao = i.getInstituicaoAtual() != null ? i.getInstituicaoAtual().getId() : null;
        Integer declaracao = i.getDeclaracaoAtual() != null ? i.getDeclaracaoAtual().getId() : null;
        return EstudanteDTO.builder()
                .matricula(i.getMatricula())
                .nome(i.getNome())
                .instituicaoAtual(instituicao)
                .declaracaoAtual(declaracao)
                .build();
    }

    @GetMapping(value = "/create")
    public ModelAndView create(EstudanteDTO estudante, ModelAndView model) {
        model.setViewName("estudantes/form");
        model.addObject("estudante", estudante);
        model.addObject("instituicoes", instituicaoService.getAll());
        model.addObject("method", "POST");
        return model;
    }

    @GetMapping(value = "/createDeclaracao")
    public ModelAndView createDeclaracao(Declaracao declaracao,
            ModelAndView model) {
        model.setViewName("estudantes/formDeclaracao");
        model.addObject("declaracao", declaracao);
        return model;
    }

    @GetMapping(value = "/createDeclaracao/{id}")
    public ModelAndView createDeclaracao(@PathVariable(name = "id") Integer id, DeclaracaoDTO declaracao,
            ModelAndView model) {
        try {
            Estudante e = this.estudanteService.getById(id).orElseThrow(() -> new EstudanteNotFoundException());
            declaracao.setEstudante(e.getMatricula());
            model.setViewName("estudantes/formDeclaracao");
            model.addObject("declaracao", declaracao);
        } catch (Exception e) {
            model.setViewName("estudantes/formDeclaracao");
            model.addObject("exception", e.getMessage());
            model.addObject("declaracao", declaracao);
        }
        return model;
    }

    @PostMapping(value = "/createDeclaracao")
    public ModelAndView saveDeclaracao(@Valid @ModelAttribute("declaracao") DeclaracaoDTO declaracao,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes ra) throws Exception {

        if (validation.hasErrors()) {
            model.addObject("declaracao", declaracao);
            model.addObject("method", "POST");
            model.setViewName("estudantes/formDeclaracao");
            return model;
        }

        try {
            this.estudanteService.emitirDeclaracao(declaracao);
        } catch (Exception e) {
            model.addObject("declaracao", declaracao);
            model.addObject("exception", e.getMessage());
            model.addObject("method", "POST");
            model.setViewName("estudantes/formDeclaracao");
            return model;
        }

        model.addObject("estudantes", estudanteService.getAll());
        model.setViewName("redirect:list");
        ra.addFlashAttribute("mensagem", "Declaração gerada com Sucesso!");
        return model;
    }

    @PostMapping(value = "/create")
    public ModelAndView saveEstudante(@Valid @ModelAttribute("estudante") EstudanteDTO estudante,
            BindingResult validation, ModelAndView model,
            RedirectAttributes ra) throws Exception {
        if (validation.hasErrors()) {
            model.addObject("estudante", estudante);
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("method", "POST");
            model.addObject("hasErrors", true);
            model.setViewName("estudantes/form");
            return model;
        }
        try {
            this.estudanteService.cadastrar(estudante);
        } catch (Exception e) {
            model.addObject("estudante", estudante);
            model.addObject("exception", e.getMessage());
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("method", "POST");
            model.setViewName("estudantes/form");
            return model;
        }
        model.setViewName("redirect:list");
        ra.addFlashAttribute("success", true);
        ra.addFlashAttribute("mensagem", "Estudante Cadastrado com Sucesso!");
        return model;
    }

    @PutMapping(value = "/create")
    public ModelAndView updateEstudante(@Valid @ModelAttribute("estudante") EstudanteDTO estudante,
            BindingResult validation, ModelAndView model,
            RedirectAttributes ra) throws Exception {
        if (validation.hasErrors()) {
            model.addObject("estudante", estudante);
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("method", "PUT");
            model.addObject("hasErrors", true);
            model.setViewName("estudantes/form");
            return model;
        }
        try {
            this.estudanteService.update(estudante);
        } catch (Exception e) {
            model.addObject("estudante", estudante);
            model.addObject("exception", e.getMessage());
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("method", "PUT");
            model.setViewName("estudantes/form");
            return model;
        }
        model.setViewName("redirect:list");
        ra.addFlashAttribute("success", true);
        ra.addFlashAttribute("mensagem", "Estudante atualizado com Sucesso!");
        return model;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteEstudante(@PathVariable(name = "id") Integer id, ModelAndView model,
            RedirectAttributes ra) {
        try {
            this.estudanteService.deleteEstudante(id);
        } catch (Exception e) {
            model.addObject("estudantes", estudanteService.getAll());
            ra.addFlashAttribute("mensagem", e.getMessage());
            model.setViewName("redirect:/estudantes/list");
            return model;
        }
        model.setViewName("redirect:/estudantes/list");
        ra.addFlashAttribute("mensagem", "Estudante removido com Sucesso!");
        return model;
    }

}
