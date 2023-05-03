package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.PeriodoLetivoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.periodo.PeriodoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.InstituicaoService;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.PeriodoLetivoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/periodos")
@RequiredArgsConstructor

public class PeriodoLetivoController {

    private final PeriodoLetivoService periodoLetivoService;

    private final InstituicaoService instituicaoService;
    // private final EstudanteService estudanteService;

    @GetMapping(value = "/list")
    public String index(Model model) {

        model.addAttribute("periodosLetivos", this.periodoLetivoService.getAll());
        return "periodoLetivo/list";
    }

    @GetMapping(value = "/create")
    public ModelAndView create(PeriodoLetivoDTO periodoLetivo, ModelAndView model) {
        model.addObject("title", "Cadastrar Periodo Letivo");
        model.addObject("periodoLetivo", periodoLetivo);
        model.addObject("instituicoes", instituicaoService.getAll());
        model.addObject("method", "POST");
        model.setViewName("periodoLetivo/form");
        return model;
    }

    @GetMapping(value = "/create/{id}")
    public ModelAndView updatePeriodoForm(@PathVariable(name = "id") Integer id, PeriodoLetivoDTO periodoLetivo,
            ModelAndView model) {
        try {
            PeriodoLetivo p = this.periodoLetivoService.getById(id)
                    .orElseThrow(() -> new PeriodoNotFoundException());
            model.addObject("title", "Atualizar Periodo Letivo");
            model.addObject("periodoLetivo", this.converter(p));
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("method", "PUT");
            model.setViewName("periodoLetivo/form");
        } catch (Exception e) {
            model.setViewName("periodoLetivo/form");
            model.addObject("exception", e.getMessage());
            model.addObject("periodoLetivo", periodoLetivo);
        }
        return model;
    }

    /*
     * @GetMapping
     * public List<PeriodoLetivoDTO> getAll(){
     *
     * return this.PeriodoLetivoService.getAll()
     * .stream().map(i -> {
     * return this.converter(i);
     * }).collect(Collectors.toList());
     * }
     */

    @PostMapping(value = "/create")
    public ModelAndView salvar(@Valid @ModelAttribute("periodoLetivo") PeriodoLetivoDTO p, BindingResult validation,
            ModelAndView model, RedirectAttributes ra)
            throws Exception {

        if (validation.hasErrors()) {
            model.addObject("title", "Cadastrar Periodo Letivo");
            model.addObject("periodoLetivo", p);
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("hasErrors", true);
            model.addObject("method", "POST");
            model.setViewName("periodoLetivo/form");
            return model;
        }
        try {
            this.periodoLetivoService.save(p);
        } catch (Exception e) {
            model.addObject("title", "Cadastrar Periodo Letivo");
            model.addObject("periodoLetivo", p);
            model.addObject("exception", e.getMessage());
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("method", "POST");
            model.setViewName("periodoLetivo/form");
            return model;
        }

        model.addObject("periodos", periodoLetivoService.getAll());
        model.setViewName("redirect:list");
        ra.addFlashAttribute("mensagem", "Periodo Cadastrado com Sucesso!");
        ra.addFlashAttribute("success", true);
        return model;
    }

    @PutMapping(value = "/create")
    public ModelAndView update(@Valid @ModelAttribute("periodoLetivo") PeriodoLetivoDTO p, BindingResult validation,
            ModelAndView model, RedirectAttributes ra)
            throws Exception {

        if (validation.hasErrors()) {
            model.addObject("periodoLetivo", p);
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("hasErrors", true);
            model.addObject("method", "POST");
            model.setViewName("periodoLetivo/form");
            return model;
        }
        try {
            this.periodoLetivoService.update(p);
        } catch (Exception e) {
            model.addObject("periodoLetivo", p);
            model.addObject("exception", e.getMessage());
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("method", "POST");
            model.setViewName("periodoLetivo/form");
            return model;
        }

        model.addObject("periodos", periodoLetivoService.getAll());
        model.setViewName("redirect:list");
        ra.addFlashAttribute("mensagem", "Periodo atualizado com Sucesso!");
        ra.addFlashAttribute("success", true);
        return model;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deletePeriodo(@PathVariable(name = "id") Integer id, ModelAndView model,
            RedirectAttributes ra) {
        try {
            this.periodoLetivoService.deletePeriodo(id);
        } catch (Exception e) {
            model.addObject("periodos", periodoLetivoService.getAll());
            ra.addFlashAttribute("mensagem", e.getMessage());
            model.setViewName("redirect:/periodos/list");
            return model;
        }
        model.setViewName("redirect:/periodos/list");
        ra.addFlashAttribute("mensagem", "Periodo removido com Sucesso!");
        return model;
    }

    private PeriodoLetivoDTO converter(PeriodoLetivo p) {
        Integer i = p.getInstituicao() != null ? p.getInstituicao().getId() : null;
        return PeriodoLetivoDTO.builder()
                .id(p.getId())
                .ano(p.getAno())
                .periodo(p.getPeriodo())
                .dataInicio(p.getDataInicio().toString())
                .dataFinal(p.getDataFinal().toString())
                .instituicao(i)
                .build();
    }

}
// a
