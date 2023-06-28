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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.builder.InstituicaoBuilder;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.InstituicaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao.InstituicaoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.InstituicaoService;

@Controller
@RequestMapping("/instituicoes")
@PreAuthorize("hasRole('ADMIN')")
public class InstituicaoController {
    // Value to set pagination quantity
    private static final int PAGE_SIZE = 10;

    @Autowired
    private InstituicaoService instituicaoService;

    @GetMapping(value = "/list")
    public ModelAndView index(ModelAndView model, Integer page) {
        page = page != null ? page : 0;
        Page<Instituicao> entityPage = this.instituicaoService.getAllPaginated(page, PAGE_SIZE);

        model.setViewName("instituicoes/list");
        model.addObject("menu", "instituicoes");
        model.addObject("instituicoes", entityPage.getContent());
        model.addObject("currentPage", entityPage.getNumber());
        model.addObject("totalPages", entityPage.getTotalPages());
        model.addObject("pagePath", "/instituicoes/list");
        model.addObject("pageNum", page);

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
        Instituicao instituicao = instituicaoService.getById(id).orElseThrow(() -> new InstituicaoNotFoundException());
        model.setViewName("instituicoes/form");
        model.addObject("instituicao", InstituicaoBuilder.convertToDTO(instituicao));
        model.addObject("method", "PUT");
        model.addObject("periodos", instituicao.getPeriodos());

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
