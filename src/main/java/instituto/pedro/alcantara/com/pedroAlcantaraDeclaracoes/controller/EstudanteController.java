package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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
    public ModelAndView list(ModelAndView model) {
        model.setViewName("estudantes/list");
        model.addObject("menu", "estudantes");
        model.addObject("estudantes", estudanteService.getAll());

        return model;
    }

    @GetMapping
    public List<Estudante> getAll() {
        return this.estudanteService.getAll();
    }

    @RequestMapping("/{id}")
    public ModelAndView queryEstudante(@PathVariable(value = "id") Integer id, ModelAndView model) {
        Optional<Estudante> estudante = estudanteService.getById(id);

        if (estudante.isPresent()) {
            model.setViewName("estudantes/form");
            model.addObject("estudante", estudante.get());
            model.addObject("instituicoes", instituicaoService.getAll());
        } else {
            model.setViewName("estudantes/list");
            model.addObject("estudantes", estudante);
        }

        return model;
    }

    // LocalDate dataInicio = LocalDate.parse(p.getDataInicio(),
    // DateTimeFormatter.ISO_LOCAL_DATE);
    private DeclaracaoDTO converterDeclaracaoDTO(Declaracao declaracao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

        return DeclaracaoDTO
                .builder()
                .id(declaracao.getId())
                .observacao(declaracao.getObservacao())
                .dataRecebimento(declaracao.getDataRecebimento().format(formatter))
                .estudante(this.converterEstudanteDTO(declaracao.getEstudante()))
                .build();
    }

    private EstudanteDTO converterEstudanteDTO(Estudante i) {

        return EstudanteDTO.builder()
                .matricula(i.getMatricula())
                .nome(i.getNome())
                .instituicaoAtual(i.getInstituicaoAtual().getId())
                .declaracaoAtual(this.converterDeclaracaoDTO(i.getDeclaracaoAtual()))
                .build();
    }

    @GetMapping
    @RequestMapping("/create")
    public ModelAndView create(Estudante estudante, ModelAndView model) {
        model.setViewName("estudantes/form");
        model.addObject("estudante", estudante);
        model.addObject("instituicoes", instituicaoService.getAll());

        return model;
    }

    @GetMapping
    @RequestMapping("/createDeclaracao/{id}")
    public ModelAndView createDeclaracao(@PathVariable(name = "id") Integer id, Declaracao declaracao,
            ModelAndView model) {
        Optional<Estudante> e = this.estudanteService.getById(id);
        declaracao.setEstudante(e.get());
        model.setViewName("estudantes/formDeclaracao");
        model.addObject("declaracao", declaracao);
        return model;
    }

    @PostMapping
    @RequestMapping("/storeDeclaracao")
    public String saveDeclaracao(@Valid DeclaracaoDTO declaracao, Model model, RedirectAttributes ra) throws Exception {
        this.estudanteService.emitirDeclaracao(declaracao);

        model.addAttribute("estudantes", estudanteService.getAll());
        ra.addFlashAttribute("mensagem", "Estudante Cadastrado com Sucesso!");
        return "redirect:list";
    }

    @PostMapping
    @RequestMapping("/store")
    public ModelAndView saveEstudante(@Valid EstudanteDTO estudante, BindingResult validation, ModelAndView model, RedirectAttributes ra) throws Exception {
        if(validation.hasErrors()){
            model.setViewName("estudantes/form");
            model.addObject("estudante", estudante);
            model.addObject("instituicoes", instituicaoService.getAll());
            return model;
        }
        this.estudanteService.cadastrar(estudante);
        model.setViewName("redirect:list");
        ra.addFlashAttribute("mensagem", "Estudante Cadastrado com Sucesso!");
        return model;
    }

}
