package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.EstudanteRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.InstituicaoRepository;

@Service

public class EstudanteService {
    
    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    public Estudante cadastrar( @Valid EstudanteDTO estudante) throws Exception{
        Estudante student = new Estudante();
        Instituicao instituicao = this.instituicaoRepository.findById(estudante.getInstituicaoAtual())
            .orElseThrow(() -> new Exception("Deu ruim"));
        student.setNome(estudante.getNome());

        student.setInstituicaoAtual(instituicao);
        this.estudanteRepository.save(student);
        return student;
    }

    public List<Estudante> getAll(){
        return this.estudanteRepository.findAll();
    }
    
}
