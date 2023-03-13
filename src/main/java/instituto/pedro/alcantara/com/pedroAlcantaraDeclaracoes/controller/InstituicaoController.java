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
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.InstituicaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/instituicoes")
@RequiredArgsConstructor


public class InstituicaoController {
    
    InstituicaoService instituicaoService;

    @GetMapping
    public List<Instituicao> getAll(){
        return this.instituicaoService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Instituicao salvar(@RequestBody @Valid Instituicao i) {
        return this.instituicaoService.save(i);
    }

}
