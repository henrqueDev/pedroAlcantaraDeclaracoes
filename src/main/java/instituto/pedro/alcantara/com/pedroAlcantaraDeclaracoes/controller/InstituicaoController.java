package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Page;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.InstituicaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.InstituicaoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/instituicoes")
@RequiredArgsConstructor

public class InstituicaoController {
    //Value to set pagination quantity
    private static final int PAGE_SIZE = 2;

    private final InstituicaoService instituicaoService;

    @GetMapping(value = "/list")
    public ModelAndView index(ModelAndView model, Integer page) {
        Page<InstituicaoDTO> entityPage = this.instituicaoService.getAllPaginated(page, PAGE_SIZE);

        model.setViewName("instituicoes/list");
        model.addObject("menu", "instituicoes");
        model.addObject("instituicoes", entityPage.getContent());
        model.addObject("currentPage", entityPage.getNumber());
        model.addObject("totalPages", entityPage.getTotalPages());
        model.addObject("pagePath", "/instituicoes/list");

        return model;
    }

    @GetMapping(value = "/create")
    public ModelAndView create(InstituicaoDTO instituicao, ModelAndView model) {

        model.setViewName("instituicoes/form");
        model.addObject("instituicao", instituicao);
        model.addObject("method", "POST");
        return model;
    }

    @GetMapping(value = "/create/{id}")
    public ModelAndView updateInstituicaoForm(@PathVariable(value = "id") Integer id, ModelAndView model) {
        try {
            Instituicao instituicao = instituicaoService.getById(id);
            model.setViewName("instituicoes/form");
            model.addObject("instituicao", Instituicao.converter(instituicao));
            model.addObject("method", "PUT");
            model.addObject("periodos", instituicao.getPeriodos());
        } catch (Exception e) {
            model.setViewName("instituicoes/form");
            model.addObject("exception", e.getMessage());
            model.addObject("instituicao", new Instituicao());
            model.addObject("method", "POST");
        }
        return model;
    }

    @PostMapping(value = "/create")
    public ModelAndView salvar(@Valid @ModelAttribute("instituicao") InstituicaoDTO i, BindingResult validation,
            ModelAndView model, RedirectAttributes ra) {

        if (validation.hasErrors()) {
            model.addObject("instituicao", i);
            model.addObject("method", "POST");
            model.addObject("hasErrors", true);
            model.setViewName("instituicoes/form");
            return model;
        }

        try {
            this.instituicaoService.save(i);
        } catch (Exception e) {
            model.addObject("instituicao", i);
            model.addObject("exception", e.getMessage());
            model.addObject("method", "POST");
            model.setViewName("instituicoes/form");
            return model;
        }

        ra.addFlashAttribute("mensagem", "A instituição foi Cadastrada com Sucesso!");
        ra.addFlashAttribute("success", true);
        model.setViewName("redirect:list");
        return model;
    }

    @PutMapping(value = "/create")
    public ModelAndView updateInstituicao(@Valid @ModelAttribute("instituicao") InstituicaoDTO i,
            BindingResult validation,
            ModelAndView model, RedirectAttributes ra) {

        if (validation.hasErrors()) {
            model.addObject("instituicao", i);
            model.addObject("method", "PUT");
            model.addObject("hasErrors", true);
            model.setViewName("instituicoes/form");
            return model;
        }

        try {
            this.instituicaoService.update(i);
        } catch (Exception e) {
            model.addObject("instituicao", i);
            model.addObject("exception", e.getMessage());
            model.addObject("method", "POST");
            model.setViewName("instituicoes/form");
            return model;
        }

        ra.addFlashAttribute("mensagem", "A instituição foi Cadastrada com Sucesso!");
        ra.addFlashAttribute("success", true);
        model.setViewName("redirect:list");
        return model;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteInstituicao(@PathVariable(name = "id") Integer id, ModelAndView model,
            RedirectAttributes ra) {
        try {
            this.instituicaoService.deleteInstituicao(id);
        } catch (Exception e) {
            ra.addFlashAttribute("mensagem", e.getMessage());
            model.setViewName("redirect:/instituicoes/list");
            return model;
        }
        model.setViewName("redirect:/instituicoes/list");
        ra.addFlashAttribute("mensagem", "Instituicao removido com Sucesso!");
        return model;
    }

}
