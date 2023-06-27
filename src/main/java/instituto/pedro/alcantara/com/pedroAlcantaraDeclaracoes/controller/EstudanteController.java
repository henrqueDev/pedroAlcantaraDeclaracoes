package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.builder.EstudanteBuilder;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.DeclaracaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.estudante.EstudanteNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao.InstituicaoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.documentos.PdfFile;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.EstudanteService;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.InstituicaoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/estudantes")
@RequiredArgsConstructor

public class EstudanteController {
    // Value to set pagination quantity
    private static final int PAGE_SIZE = 2;

    private final EstudanteService estudanteService;
    private final InstituicaoService instituicaoService;

    @GetMapping(value = "/list")
    public ModelAndView list(ModelAndView model, Integer page) {
        page = page != null ? page : 0;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Estudante> entityPage = estudanteService.getAll(pageable);
        model.addObject("estudantes", entityPage.getContent());
        model.addObject("currentPage", entityPage.getNumber());
        model.addObject("totalPages", entityPage.getTotalPages());
        model.addObject("pagePath", "/estudantes/list");
        model.addObject("pageNum", page);
        model.setViewName("estudantes/list");
        model.addObject("menu", "estudantes");

        return model;
    }

    @GetMapping(value = "/list/instituicao/{id}")
    public ModelAndView listByInstituicao(@PathVariable(value = "id") Integer id, ModelAndView model) {
        model.setViewName("estudantes/list");
        model.addObject("menu", "estudantes");
        model.addObject("estudantes", instituicaoService.getAllEstudantesById(id));

        return model;
    }

    @GetMapping(value = "/create/{id}")
    public ModelAndView updateEstudanteForm(@PathVariable(value = "id") Integer id, ModelAndView model, Integer page) {
        Optional<Estudante> estudante = estudanteService.getById(id);
        if (estudante.isPresent()) {
            model.setViewName("estudantes/form");
            model.addObject("method", "PUT");
            model.addObject("estudante", EstudanteBuilder.convertToDTO(estudante.get()));
            model.addObject("instituicoes", instituicaoService.getAllWithoutPagination());
        } else {
            model.setViewName("estudantes/form");
            model.addObject("estudante", EstudanteBuilder.convertToDTO(new Estudante()));
            model.addObject("method", "POST");
            model.addObject("instituicoes", instituicaoService.getAllWithoutPagination());

            Pageable pageable = PageRequest.of(page != null ? page : 0, PAGE_SIZE);
            Page<Estudante> entityPage = estudanteService.getAll(pageable);
            model.addObject("estudantes", entityPage.getContent());
        }

        return model;
    }

    @GetMapping(value = "/create")
    public ModelAndView create(EstudanteDTO estudante, ModelAndView model) {
        model.setViewName("estudantes/form");
        model.addObject("estudante", estudante);
        model.addObject("instituicoes", instituicaoService.getAllWithoutPagination());
        model.addObject("method", "POST");
        return model;
    }

    @GetMapping(value = "/createDeclaracao")
    public ModelAndView createDeclaracao(Declaracao declaracao,
            ModelAndView model) {
        model.setViewName("estudantes/formDeclaracao");
        model.addObject("declaracao", declaracao);

        return model;
    }

    @GetMapping(value = "/createDeclaracao/{id}")
    public ModelAndView createDeclaracao(@PathVariable(name = "id") Integer id, DeclaracaoDTO declaracao,
            ModelAndView model) {
        try {
            Estudante e = this.estudanteService.getById(id).orElseThrow(() -> new EstudanteNotFoundException());
            declaracao.setEstudante(e.getMatricula());
            Instituicao i = e.getInstituicaoAtual();
            if (i == null) {
                throw new InstituicaoNotFoundException();
            }

            model.setViewName("estudantes/formDeclaracao");
            model.addObject("nome", e.getNome());
            model.addObject("periodos", i != null ? i.getPeriodos() : null);
            model.addObject("declaracao", declaracao);
        } catch (Exception e) {
            model.setViewName("estudantes/formDeclaracao");
            model.addObject("exception", e.getMessage());
        }
        return model;
    }

    @PostMapping(value = "/createDeclaracao")
    public ModelAndView saveDeclaracao(@Valid @ModelAttribute("declaracao") DeclaracaoDTO declaracao,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes ra) throws Exception {

        try {
            boolean noArquivo = declaracao.getArquivoPDF().getSize() == 0 ? true : false;
            if (validation.hasErrors() || noArquivo) {
                model.addObject("declaracao", declaracao);
                Estudante e = this.estudanteService.getById(declaracao.getEstudante())
                        .orElseThrow(() -> new EstudanteNotFoundException());
                declaracao.setEstudante(e.getMatricula());
                Instituicao i = e.getInstituicaoAtual();
                model.addObject("periodos", i != null ? i.getPeriodos() : null);
                model.addObject("method", "POST");
                model.addObject("hasErrors", true);
                model.addObject("noFileError", noArquivo);
                model.setViewName("estudantes/formDeclaracao");
                return model;
            }

            this.estudanteService.emitirDeclaracao(declaracao);
        } catch (Exception e) {
            model.addObject("declaracao", declaracao);
            model.addObject("exception", e.getMessage());
            model.addObject("method", "POST");
            model.setViewName("estudantes/formDeclaracao");
            return model;
        }

        model.setViewName("redirect:list");
        ra.addFlashAttribute("mensagem", "Declaração gerada com Sucesso!");
        ra.addFlashAttribute("success", true);
        return model;
    }

    @GetMapping("/download/declaracao/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable(name = "id") Integer id) {
        PdfFile file = estudanteService.getDeclaracaoPDF(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getNome() + "\"")
                .body(file.getBytes());
    }

    @PostMapping(value = "/create")
    public ModelAndView saveEstudante(@Valid @ModelAttribute("estudante") EstudanteDTO estudante,
            BindingResult validation, ModelAndView model,
            RedirectAttributes ra) throws Exception {
        if (validation.hasErrors()) {
            model.addObject("estudante", estudante);
            model.addObject("instituicoes", instituicaoService.getAllWithoutPagination());
            model.addObject("method", "POST");
            model.addObject("hasErrors", true);
            model.setViewName("estudantes/form");
            return model;
        }
        try {
            this.estudanteService.cadastrar(estudante);
        } catch (Exception e) {
            model.addObject("estudante", estudante);
            model.addObject("exception", e.getMessage());
            model.addObject("instituicoes", instituicaoService.getAllWithoutPagination());
            model.addObject("method", "POST");
            model.setViewName("estudantes/form");
            return model;
        }
        model.setViewName("redirect:list");
        ra.addFlashAttribute("success", true);
        ra.addFlashAttribute("mensagem", "Estudante Cadastrado com Sucesso!");
        return model;
    }

    @PutMapping(value = "/create")
    public ModelAndView updateEstudante(@Valid @ModelAttribute("estudante") EstudanteDTO estudante,
            BindingResult validation, ModelAndView model,
            RedirectAttributes ra) throws Exception {
        if (validation.hasErrors()) {
            model.addObject("estudante", estudante);
            model.addObject("instituicoes", instituicaoService.getAllWithoutPagination());
            model.addObject("method", "PUT");
            model.addObject("hasErrors", true);
            model.setViewName("estudantes/form");
            return model;
        }
        try {
            this.estudanteService.update(estudante);
        } catch (Exception e) {
            model.addObject("estudante", estudante);
            model.addObject("exception", e.getMessage());
            model.addObject("instituicoes", instituicaoService.getAllWithoutPagination());
            model.addObject("method", "PUT");
            model.setViewName("estudantes/form");
            return model;
        }
        model.setViewName("redirect:list");
        ra.addFlashAttribute("success", true);
        ra.addFlashAttribute("mensagem", "Estudante atualizado com Sucesso!");
        return model;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteEstudante(@PathVariable(name = "id") Integer id, ModelAndView model,
            RedirectAttributes ra) {
        try {
            this.estudanteService.deleteEstudante(id);
        } catch (Exception e) {
            ra.addFlashAttribute("mensagem", e.getMessage());
            model.setViewName("redirect:/estudantes/list");
            return model;
        }
        model.setViewName("redirect:/estudantes/list");
        ra.addFlashAttribute("mensagem", "Estudante removido com Sucesso!");
        return model;
    }

}
