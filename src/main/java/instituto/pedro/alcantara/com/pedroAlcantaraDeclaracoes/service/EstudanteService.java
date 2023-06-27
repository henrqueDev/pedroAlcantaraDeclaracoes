package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.DeclaracaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.estudante.EstudanteInstituicaoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.estudante.EstudanteNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao.InstituicaoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao.InstituicaoWithoutPeriodoException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.periodo.PeriodoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.documentos.PdfFile;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.DeclaracaoRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.EstudanteRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.InstituicaoRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.PdfFileRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.PeriodoLetivoRepository;

@Service

public class EstudanteService {

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private DeclaracaoRepository declaracaoRepository;

    @Autowired
    private PdfFileRepository pdfFileRepository;

    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;

    @Transactional
    public void update(@Valid EstudanteDTO estudante) throws Exception {
        Estudante student = this.estudanteRepository.findById(estudante.getMatricula())
                .orElseThrow(() -> new Exception());

        Instituicao instituicao = estudante.getInstituicaoAtual() != null
                ? this.instituicaoRepository.findById(estudante.getInstituicaoAtual())
                        .orElseThrow(() -> new InstituicaoNotFoundException())
                : null;

        if (instituicao != null && student != null) {
            student.setInstituicaoAtual(instituicao);
            student.setNome(estudante.getNome());
            student.setDeclaracaoAtual(null);

        } else {
            student.setInstituicaoAtual(null);
            student.setNome(estudante.getNome());
            student.setDeclaracaoAtual(null);
        }
        this.estudanteRepository.updateEstudante(student.getMatricula(), student.getNome(),
                student.getInstituicaoAtual());
    }

    @Transactional
    public Estudante cadastrar(@Valid EstudanteDTO estudante) throws Exception {
        Estudante student = new Estudante();
        if (estudante.getInstituicaoAtual() != null) {
            Instituicao instituicao = this.instituicaoRepository.findById(estudante.getInstituicaoAtual())
                    .orElseThrow(() -> new InstituicaoNotFoundException());
            student.setInstituicaoAtual(instituicao);
        }
        student.setNome(estudante.getNome());
        return this.estudanteRepository.save(student);
    }

    @Transactional
    public void deleteEstudante(Integer estudante) {
        Estudante e = this.estudanteRepository.findById(estudante).orElseThrow(() -> new EstudanteNotFoundException());
        List<Declaracao> declaracoes = e.getDeclaracoes();
        for (Declaracao declaracao : declaracoes) {
            this.declaracaoRepository.delete(declaracao);
        }

        this.estudanteRepository.delete(e);
    }

    @Transactional
    public void emitirDeclaracao(@Valid DeclaracaoDTO d) throws Exception {
        LocalDate dataRecebimento = LocalDate.now();

        Estudante e = this.estudanteRepository.findById(d.getEstudante())
                .orElseThrow(() -> new InstituicaoNotFoundException());

        PeriodoLetivo p = this.periodoLetivoRepository.findById(d.getPeriodo())
                .orElseThrow(() -> new PeriodoNotFoundException());

        if (e.getInstituicaoAtual() != null) {
            if (e.getInstituicaoAtual().getPeriodos() != null) {

                PdfFile pdf = new PdfFile(e.getNome() + e.getMatricula() + p.getAno() + p.getPeriodo() + ".pdf",
                        d.getArquivoPDF().getBytes());

                Declaracao declaracao = new Declaracao(d.getObservacao(), dataRecebimento, e, p,
                        pdf);
                e.setDeclaracaoAtual(declaracao);
                this.declaracaoRepository.save(declaracao);
            } else {
                throw new InstituicaoWithoutPeriodoException();
            }
        } else {
            throw new EstudanteInstituicaoNotFoundException();
        }
    }

    public Page<Estudante> getAll(Pageable pageable) {
        return this.estudanteRepository.findAll(pageable);
    }

    public Page<Estudante> getAllWithoutDeclaracao(Pageable pageable) {
        return this.estudanteRepository.findAllEstudantesWithoutDeclaracao(pageable);
    }

    public Optional<Declaracao> getDeclaracaoById(Integer id) {
        return this.declaracaoRepository.findById(id);
    }

    public PdfFile getDeclaracaoPDF(Integer id) {
        return this.pdfFileRepository.findById(id).get();
    }

    public Optional<Estudante> getById(Integer id) {
        return this.estudanteRepository.findById(id);
    }

}
