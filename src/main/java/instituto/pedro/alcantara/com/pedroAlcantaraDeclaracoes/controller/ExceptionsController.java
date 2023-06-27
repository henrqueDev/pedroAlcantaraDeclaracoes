package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionsController {

    // For UI Pages
    @ExceptionHandler(Exception.class)
    public ModelAndView Exception(Exception e, ModelAndView model) {
        model.setViewName("error");
        model.addObject("mensagem", e.getMessage());
        return model;
    }

}