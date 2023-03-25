package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
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

    @GetMapping
    @RequestMapping("/create")
    public String create(Estudante estudante, Model model) {

        model.addAttribute("estudante", estudante);
        model.addAttribute("instituicoes", instituicaoService.getAll());
        return "estudantes/form";
    }

    @PostMapping
    @RequestMapping("/store")
    public String saveEstudante(@Valid EstudanteDTO estudante, Model model) throws Exception{
        this.estudanteService.cadastrar(estudante);

        model.addAttribute("estudantes", estudanteService.getAll());
        return "estudantes/list";
    }

}
