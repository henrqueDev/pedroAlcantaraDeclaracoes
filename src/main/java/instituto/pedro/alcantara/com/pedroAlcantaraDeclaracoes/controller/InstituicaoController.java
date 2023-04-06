package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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

    @GetMapping
    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model) {

        model.setViewName("instituicoes/list");
        model.addObject("instituicoes", getAll());
        return model;
    }

    @GetMapping
    @RequestMapping("/create")
    public ModelAndView create(InstituicaoDTO instituicao, ModelAndView model) {

        model.setViewName("instituicoes/form");
        model.addObject("instituicao", instituicao);
        return model;
    }

    @GetMapping
    public List<InstituicaoDTO> getAll(){

        return this.instituicaoService.getAll()
            .stream().map(i -> {
                return this.converter(i);
            }).collect(Collectors.toList());
    }

    @GetMapping
    @RequestMapping("/{id}/alunos")
    public InstituicaoDTO getEstudanteByInstituicao(@PathVariable("id") Integer id) throws Exception{   
        return this.instituicaoService.getAllAlunosByInstituicao(id)
        .map(e -> converter(e))
        .orElseThrow(() -> new Exception("Exception"));
    }

    @PostMapping
    @RequestMapping("/store")
    public String salvar(@Valid InstituicaoDTO i, Model model, RedirectAttributes ra) {
        this.instituicaoService.save(i);

        ra.addFlashAttribute("mensagem", "A instituição foi Cadastrada com Sucesso!");
        model.addAttribute("instituicoes", getAll());
        return "redirect:list";
    }

    private List<EstudanteDTO> converterEstudanteDTO(List<Estudante> e){
        return e.stream().map(estudante -> {
           return EstudanteDTO
                .builder()
                    .matricula(estudante.getMatricula())
                    .nome(estudante.getNome())
                    .instituicaoAtual(estudante.getInstituicaoAtual().getId())
                .build();
        }).collect(Collectors.toList());
          
    }

    private InstituicaoDTO converter(Instituicao i){
        
        return InstituicaoDTO.builder()
        .nome(i.getNome())
        .sigla(i.getSigla())
        .fone(i.getFone())
        .alunos(this.converterEstudanteDTO(i.getAlunos()))
        .build();
            
    }

}
//a