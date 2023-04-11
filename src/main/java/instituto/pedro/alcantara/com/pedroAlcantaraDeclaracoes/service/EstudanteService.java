package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.DeclaracaoRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.EstudanteRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.InstituicaoRepository;

@Service

public class EstudanteService {
    
    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    // @Autowired 
    // private DeclaracaoRepository declaracaoRepository;

    @Transactional
    public Estudante cadastrar( @Valid EstudanteDTO estudante) throws Exception{
        Estudante student = new Estudante();
        Instituicao instituicao = this.instituicaoRepository.findById(estudante.getInstituicaoAtual())
            .orElseThrow(() -> new Exception("Deu ruim"));
        List<Estudante> alunos = instituicao.getAlunos();
        // LocalDate data = LocalDate.now();
        // Declaracao declaracao = new Declaracao("macacoooorrrr", data, student );
        // this.declaracaoRepository.save(declaracao);
        // student.setDeclaracaoAtual();
         student.setNome(estudante.getNome());
         student.setInstituicaoAtual(instituicao);
        // student.setDeclaracaoAtual(declaracao);
        //Estudante student = new Estudante(instituicao, estudante.getNome(), new Declaracao());
        alunos.add(student);
        instituicao.setAlunos(alunos);
        return this.estudanteRepository.save(student);
    }

    public List<Estudante> getAll(){
        return this.estudanteRepository.findAll();
    }
    
}
