package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.DeclaracaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.InstituicaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.EstudanteService;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.InstituicaoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/estudantes")
@RequiredArgsConstructor

public class EstudanteController {

    private final EstudanteService estudanteService;
    private final InstituicaoService instituicaoService;

    @GetMapping
    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("estudantes", estudanteService.getAll());
        return "estudantes/list";
    }

    @GetMapping
    public List<Estudante> getAll(){
        return this.estudanteService.getAll();
    }

    private DeclaracaoDTO converterDeclaracaoDTO(Declaracao declaracao){
           return DeclaracaoDTO
                .builder()
                    .id(declaracao.getId())
                    .observacao(declaracao.getObservacao())
                    .dataRecebimento(declaracao.getDataRecebimento())
                    .estudante(declaracao.getEstudante().getMatricula())
                .build();
        }

    private EstudanteDTO converter(Estudante i){
        
        return EstudanteDTO.builder()
        .matricula(i.getMatricula())
        .nome(i.getNome())
        .instituicaoAtual(i.getInstituicaoAtual().getId())
        .declaracaoAtual(this.converterDeclaracaoDTO(i.getDeclaracaoAtual()))
        .build();
            
    }

}
