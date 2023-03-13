package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.EstudanteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/estudantes")
@RequiredArgsConstructor

public class EstudanteController {

    private final EstudanteService estudanteService;

    @GetMapping
    public List<Estudante> getAll(){
        return this.estudanteService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer saveEstudante(@RequestBody @Valid EstudanteDTO estudante) throws Exception{
        Estudante estudanteCadastrado = this.estudanteService.cadastrar(estudante);
        return estudanteCadastrado.getMatricula();
    }

}
