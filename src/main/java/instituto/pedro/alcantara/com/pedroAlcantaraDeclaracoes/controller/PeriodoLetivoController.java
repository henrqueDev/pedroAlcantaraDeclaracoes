package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.PeriodoLetivoDTO;
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
    public String create(PeriodoLetivo periodoLetivo, Model model) {

        model.addAttribute("periodoLetivo", periodoLetivo);
        model.addAttribute("instituicoes", instituicaoService.getAll());
        return "periodoLetivo/form";
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
            model.addObject("periodoLetivo", p);
            model.addObject("instituicoes", instituicaoService.getAll());
            model.addObject("hasErrors", true);
            model.setViewName("periodoLetivo/form");
            return model;
        }
        try {
            this.periodoLetivoService.save(p);
        } catch (Exception e) {
            model.addObject("periodoLetivo", p);
            model.addObject("exception", e.getMessage());
            model.addObject("instituicoes", instituicaoService.getAll());
            model.setViewName("periodoLetivo/form");
            return model;
        }

        model.addObject("periodos", periodoLetivoService.getAll());
        model.setViewName("redirect:list");
        ra.addFlashAttribute("mensagem", "Periodo Cadastrado com Sucesso!");
        ra.addFlashAttribute("success", true);
        return model;
    }

    /*
     * private PeriodoLetivoDTO converter(PeriodoLetivo i){
     *
     * return PeriodoLetivoDTO.builder()
     * .nome(i.getNome())
     * .sigla(i.getSigla())
     * .fone(i.getFone())
     * .alunos(this.converterEstudanteDTO(i.getAlunos()))
     * .build();
     *
     * }
     */

}
// a
