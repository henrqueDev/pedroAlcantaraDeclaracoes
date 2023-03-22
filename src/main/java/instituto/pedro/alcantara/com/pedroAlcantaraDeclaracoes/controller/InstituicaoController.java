package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.InstituicaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.EstudanteService;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.InstituicaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/instituicoes")
@RequiredArgsConstructor

public class InstituicaoController {
    
    private final InstituicaoService instituicaoService;
    private final EstudanteService estudanteService;

    @GetMapping
    public List<Instituicao> getAll(){
        return this.instituicaoService.getAll();
    }

    @GetMapping
    @RequestMapping("/{id}/alunos")
    public InstituicaoDTO getEstudanteByInstituicao(@PathVariable("id") Integer id) throws Exception{   
        return this.instituicaoService.getAllAlunosByInstituicao(id)
        .map(e -> converter(e))
        .orElseThrow(() -> new Exception("Exception"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer salvar(@RequestBody @Valid InstituicaoDTO i) {
        Instituicao instituicao = this.instituicaoService.save(i);
        return instituicao.getId();
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