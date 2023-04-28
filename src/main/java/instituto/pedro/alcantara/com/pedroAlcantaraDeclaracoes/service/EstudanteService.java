package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.DeclaracaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao.InstituicaoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao.InstituicaoWithoutPeriodoException;
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

    @Autowired
    private DeclaracaoRepository declaracaoRepository;

    @Transactional
    public Estudante cadastrar(@Valid EstudanteDTO estudante) throws Exception {
        Estudante student = new Estudante();
        Instituicao instituicao = this.instituicaoRepository.findById(estudante.getInstituicaoAtual())
                .orElseThrow(() -> new InstituicaoNotFoundException());
        List<Estudante> alunos = instituicao.getAlunos();
        student.setNome(estudante.getNome());
        student.setInstituicaoAtual(instituicao);
        alunos.add(student);
        instituicao.setAlunos(alunos);
        return this.estudanteRepository.save(student);
    }

    @Transactional
    public void emitirDeclaracao(@Valid DeclaracaoDTO d) throws Exception {
        LocalDate dataRecebimento = LocalDate.now();
        Estudante e = this.estudanteRepository.findById(d.getEstudante().getMatricula())
                .orElseThrow(() -> new InstituicaoNotFoundException());
        try {
            if (e.getInstituicaoAtual().getPeriodoAtual() != null) {
                Declaracao declaracao = new Declaracao(d.getObservacao(), dataRecebimento, e);
                e.setDeclaracaoAtual(declaracao);
                this.declaracaoRepository.save(declaracao);
            } else {
                new InstituicaoWithoutPeriodoException();
            }
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public List<Estudante> getAll() {
        return this.estudanteRepository.findAll();
    }

    public Optional<Estudante> getById(Integer id) {
        return this.estudanteRepository.findById(id);
    }

}
