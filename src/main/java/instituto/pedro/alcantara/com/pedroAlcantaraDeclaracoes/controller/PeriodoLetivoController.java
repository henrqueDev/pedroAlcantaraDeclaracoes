package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.PeriodoLetivoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.PeriodoLetivoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/periodos")
@RequiredArgsConstructor

public class PeriodoLetivoController {
    
    private final PeriodoLetivoService PeriodoLetivoService;
    // private final EstudanteService estudanteService;

    @GetMapping
    @RequestMapping("/list")
    public String index(Model model) {

        model.addAttribute("instituicoes", new Object());
        return "instituicoes/list";
    }

    @GetMapping
    @RequestMapping("/create")
    public String create(PeriodoLetivoDTO PeriodoLetivo, Model model) {

        model.addAttribute("PeriodoLetivo", PeriodoLetivo);
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

    public String salvar(@Valid PeriodoLetivoDTO i, Model model) {
        // this.PeriodoLetivoService.save(i);

        model.addAttribute("periodoLetivo", new Object());
        return "periodoLetivo/list";
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