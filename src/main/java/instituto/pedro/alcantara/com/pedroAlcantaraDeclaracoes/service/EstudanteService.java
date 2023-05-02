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
    public void update(@Valid EstudanteDTO estudante) throws Exception {
        Estudante student = estudante.getMatricula() != null
                ? this.estudanteRepository.findById(estudante.getMatricula()).get()
                : null;
        if (estudante.getInstituicaoAtual() != null) {
            Instituicao instituicao = this.instituicaoRepository.findById(estudante.getInstituicaoAtual())
                    .orElseThrow(() -> new InstituicaoNotFoundException());

            List<Estudante> alunos = instituicao.getAlunos();
            student.setInstituicaoAtual(instituicao);
            alunos.add(student);
            instituicao.setAlunos(alunos);
        }
        student.setNome(estudante.getNome());
        this.estudanteRepository.updateEstudante(student.getMatricula(), student.getNome(),
                student.getInstituicaoAtual());
    }

    @Transactional
    public Estudante cadastrar(@Valid EstudanteDTO estudante) throws Exception {
        Estudante student = new Estudante();
        if (estudante.getInstituicaoAtual() != null) {
            Instituicao instituicao = this.instituicaoRepository.findById(estudante.getInstituicaoAtual())
                    .orElseThrow(() -> new InstituicaoNotFoundException());

            List<Estudante> alunos = instituicao.getAlunos();
            student.setInstituicaoAtual(instituicao);
            alunos.add(student);
            instituicao.setAlunos(alunos);
        }
        student.setNome(estudante.getNome());
        return this.estudanteRepository.save(student);
    }

    @Transactional
    public void emitirDeclaracao(@Valid DeclaracaoDTO d) throws Exception {
        LocalDate dataRecebimento = LocalDate.now();

        Estudante e = this.estudanteRepository.findById(d.getEstudante())
                .orElseThrow(() -> new InstituicaoNotFoundException());
        if (e.getInstituicaoAtual().getPeriodoAtual() != null) {
            Declaracao declaracao = new Declaracao(d.getObservacao(), dataRecebimento, e);
            e.setDeclaracaoAtual(declaracao);
            this.estudanteRepository.save(e);
            this.declaracaoRepository.save(declaracao);
        } else {
            throw new InstituicaoWithoutPeriodoException();
        }

    }

    public List<Estudante> getAll() {
        return this.estudanteRepository.findAll();
    }

    public Optional<Estudante> getById(Integer id) {
        return this.estudanteRepository.findById(id);
    }

}
