package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public ModelAndView list(ModelAndView model) {
        model.setViewName("estudantes/list");
        model.addObject("estudantes", estudanteService.getAll());
        
        return model;
    }

    @GetMapping
    public List<Estudante> getAll(){
        return this.estudanteService.getAll();
    }

    @GetMapping
    @RequestMapping("/create")
    public ModelAndView create(Estudante estudante, ModelAndView model) {
        model.setViewName("estudantes/form");
        model.addObject("estudante", estudante);
        model.addObject("instituicoes", instituicaoService.getAll());
        
        return model;
    }

    @PostMapping
    @RequestMapping("/store")
    public String saveEstudante(@Valid EstudanteDTO estudante, Model model, RedirectAttributes ra) throws Exception{
        this.estudanteService.cadastrar(estudante);

        model.addAttribute("estudantes", estudanteService.getAll());
        ra.addFlashAttribute("mensagem", "Estudante Cadastrado com Sucesso!");
        return "redirect:list";
    }

}
