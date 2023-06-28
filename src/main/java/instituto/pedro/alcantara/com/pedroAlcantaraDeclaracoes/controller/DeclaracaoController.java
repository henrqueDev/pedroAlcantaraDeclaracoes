package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.DeclaracaoService;

@Controller
@RequestMapping("/declaracoes")

public class DeclaracaoController {
    // Value to set pagination quantity
    private static final int PAGE_SIZE = 10;

    @Autowired
    private DeclaracaoService declaracaoService;

    @GetMapping(value = "/listAVencer")
    public ModelAndView listAVencer(ModelAndView model, Integer vencerEmDias, Integer page) {
        page = page != null ? page : 0;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        vencerEmDias = vencerEmDias != null ? vencerEmDias : 30;
        Page<Declaracao> entityPage = declaracaoService.getAllVencerDias(vencerEmDias, pageable);
        model.addObject("declaracoes", entityPage.getContent());
        model.addObject("currentPage", entityPage.getNumber());
        model.addObject("totalPages", entityPage.getTotalPages());
        model.addObject("pagePath", "/declaracoes/listAVencer");
        model.addObject("pageNum", page);
        model.addObject("vencerEmDias", vencerEmDias);
        model.setViewName("declaracoes/listAVencer");
        model.addObject("menu", "declaracoes");

        return model;
    }

    @GetMapping(value = "/listVencidas")
    public ModelAndView listVencidas(ModelAndView model, Integer page) {
        page = page != null ? page : 0;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Declaracao> entityPage = declaracaoService.getAllVencidas(pageable);
        model.addObject("declaracoes", entityPage.getContent());
        model.addObject("currentPage", entityPage.getNumber());
        model.addObject("totalPages", entityPage.getTotalPages());
        model.addObject("pagePath", "/declaracoes/listVencidas");
        model.addObject("pageNum", page);
        model.setViewName("declaracoes/listVencidas");
        model.addObject("menu", "declaracoes");

        return model;
    }

}
