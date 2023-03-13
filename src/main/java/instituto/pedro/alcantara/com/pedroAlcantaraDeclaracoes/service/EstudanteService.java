package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.EstudanteRepository;

@Service

public class EstudanteService {
    
    @Autowired
    private EstudanteRepository estudanteRepository;

    public Estudante cadastrar( @Valid Estudante estudante){
        return this.estudanteRepository.save(estudante);
    }
    
}
