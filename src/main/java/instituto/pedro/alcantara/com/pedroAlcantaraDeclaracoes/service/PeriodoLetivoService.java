package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.PeriodoLetivoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.ExceptionList;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao.InstituicaoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.periodo.PeriodoInvalidoException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.periodo.PeriodoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.periodo.PeriodoNotMatchLastException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.DeclaracaoRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.EstudanteRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.InstituicaoRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.PeriodoLetivoRepository;

@Service
public class PeriodoLetivoService {

    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private DeclaracaoRepository declaracaoRepository;

    @Transactional
    public PeriodoLetivo save(@Valid PeriodoLetivoDTO p) throws Exception {
        PeriodoLetivo periodo = new PeriodoLetivo();
        Instituicao i = instituicaoRepository.findById(p.getInstituicao())
                .orElseThrow(() -> new InstituicaoNotFoundException());
        List<PeriodoLetivo> periodosInstituicao = i.getPeriodos();

        /* Perguntar pro professor */
        for (Estudante estudante : i.getAlunos()) {
            estudante.setDeclaracaoAtual(null);
        }

        LocalDate dataInicio = LocalDate.parse(p.getDataInicio(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate dataFinal = LocalDate.parse(p.getDataFinal(), DateTimeFormatter.ISO_LOCAL_DATE);
        periodo.setPeriodo(p.getPeriodo());
        periodo.setDataInicio(dataInicio);
        periodo.setDataFinal(dataFinal);

        if (dataInicio.isAfter(dataFinal) || dataInicio.isEqual(dataFinal)
                || dataInicio.getYear() != dataFinal.getYear()) {
            throw new PeriodoInvalidoException();
        }

        periodo.setAno(p.getAno());
        periodo.setInstituicao(i);
        periodosInstituicao.add(periodo);
        i.setPeriodos(periodosInstituicao);
        i.setPeriodoAtual(periodo);
        return periodoLetivoRepository.save(periodo);
    }

    @Transactional
    public void update(@Valid PeriodoLetivoDTO p) throws Exception {

        PeriodoLetivo periodo = this.periodoLetivoRepository.findById(p.getId())
                .orElseThrow(() -> new PeriodoNotFoundException());

        Instituicao i = instituicaoRepository.findById(p.getInstituicao())
                .orElseThrow(() -> new InstituicaoNotFoundException());

        List<PeriodoLetivo> periodosInstituicao = i.getPeriodos();

        LocalDate dataInicio = LocalDate.parse(p.getDataInicio(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate dataFinal = LocalDate.parse(p.getDataFinal(), DateTimeFormatter.ISO_LOCAL_DATE);

        if (dataInicio.isAfter(dataFinal) || dataInicio.isEqual(dataFinal)
                || dataInicio.getYear() != dataFinal.getYear()) {
            throw new PeriodoInvalidoException();
        }

        periodo.setPeriodo(p.getPeriodo());
        periodo.setDataInicio(dataInicio);
        periodo.setDataFinal(dataFinal);
        periodo.setAno(p.getAno());
        i.setPeriodos(periodosInstituicao);
        this.periodoLetivoRepository.update(periodo.getId(), periodo.getAno(), periodo.getPeriodo(),
                periodo.getDataInicio(),
                periodo.getDataFinal());

    }

    @Transactional
    public void deletePeriodo(Integer periodo) {
        PeriodoLetivo p = this.periodoLetivoRepository.findById(periodo)
                .orElseThrow(() -> new InstituicaoNotFoundException());

        List<Declaracao> declaracoes = p.getDeclaracoes();
        for (Declaracao declaracao : declaracoes) {
            this.declaracaoRepository.delete(declaracao);
        }
        List<Estudante> estudantes = this.estudanteRepository.findAllEstudantesByPeriodo(p);
        for (Estudante estudante : estudantes) {
            estudante.setDeclaracaoAtual(null);
        }

        p.getInstituicao().setPeriodoAtual(null);
        this.periodoLetivoRepository.delete(p);
    }

    public Page<PeriodoLetivo> getAll(Pageable pageable) {
        return this.periodoLetivoRepository.findAll(pageable);
    }

    public Optional<PeriodoLetivo> getById(Integer id) {
        return this.periodoLetivoRepository.findById(id);
    }

}
