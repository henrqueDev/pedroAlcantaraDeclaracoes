package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.PeriodoLetivoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.ExceptionList;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao.InstituicaoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.periodo.PeriodoInvalidoException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.periodo.PeriodoNotMatchLastException;
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

        LocalDate dataInicio = LocalDate.parse(p.getDataInicio(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate dataFinal = LocalDate.parse(p.getDataFinal(), DateTimeFormatter.ISO_LOCAL_DATE);
        periodo.setPeriodo(p.getPeriodo());
        periodo.setDataInicio(dataInicio);
        periodo.setDataFinal(dataFinal);

        if (dataInicio.isAfter(dataFinal) || dataInicio.isEqual(dataFinal)
                || dataInicio.getYear() != dataFinal.getYear()) {
            throw new PeriodoInvalidoException();
        }
        for (PeriodoLetivo periodoLetivo : periodosInstituicao) {
            if (!periodo.checkLastPeriodoData(periodoLetivo)) {
                throw new PeriodoNotMatchLastException();
            }
        }
        for (Estudante estudante : estudanteRepository.findAll()) {
            if (estudante.getDeclaracaoAtual() != null) {
                declaracaoRepository.deleteDeclaracaoByEstudanteMatricula(estudante.getMatricula());
            }
        }
        periodo.setAno(p.getAno());
        periodo.setInstituicao(i);
        periodosInstituicao.add(periodo);
        i.setPeriodos(periodosInstituicao);
        i.setPeriodoAtual(periodo);
        return periodoLetivoRepository.save(periodo);
    }

    public List<PeriodoLetivo> getAll() {
        return this.periodoLetivoRepository.findAll();
    }

}
