package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service.DeclaracaoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/declaracoes")
@RequiredArgsConstructor

public class DeclaracaoController {
    // Value to set pagination quantity
    private static final int PAGE_SIZE = 2;

    private final DeclaracaoService declaracaoService;

    @GetMapping(value = "/listVencidas")
    public ModelAndView listVencidas(ModelAndView model, Integer numLimite, Integer page) {
        page = page != null ? page : 0;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Declaracao> entityPage = numLimite != null ? declaracaoService.getAllVencerDias(numLimite, pageable)
                : declaracaoService.getAllVencidas(pageable);
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
