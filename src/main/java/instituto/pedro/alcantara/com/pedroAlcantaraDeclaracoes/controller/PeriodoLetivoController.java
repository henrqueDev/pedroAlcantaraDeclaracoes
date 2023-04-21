package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping
    @RequestMapping("/list")
    public String index(Model model) {

        model.addAttribute("periodoLetivo", new Object());
        return "periodoLetivo/list";
    }

    @GetMapping
    @RequestMapping("/create")
    public String create(PeriodoLetivo periodoLetivo, Model model) {

        model.addAttribute("periodoLetivo", periodoLetivo);
        model.addAttribute("instituicoes", instituicaoService.getAll());
        return "periodoLetivo/form";
    }

    /*@GetMapping
    public List<PeriodoLetivoDTO> getAll(){

        return this.PeriodoLetivoService.getAll()
            .stream().map(i -> {
                return this.converter(i);
            }).collect(Collectors.toList());
    }*/


    @PostMapping
    @RequestMapping("/store")
    public String salvar(@Valid PeriodoLetivoDTO p, Model model, RedirectAttributes ra) throws Exception {
        // this.PeriodoLetivoService.save(i);
        this.periodoLetivoService.save(p);

        model.addAttribute("periodos", periodoLetivoService.getAll());
        ra.addFlashAttribute("mensagem", "Periodo Cadastrado com Sucesso!");
        return "redirect:list";
    }

    /* 
    private PeriodoLetivoDTO converter(PeriodoLetivo i){
        
        return PeriodoLetivoDTO.builder()
        .nome(i.getNome())
        .sigla(i.getSigla())
        .fone(i.getFone())
        .alunos(this.converterEstudanteDTO(i.getAlunos()))
        .build();
            
    }
    */

}
//a