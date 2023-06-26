package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.estudante.EstudanteNotFoundException;

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