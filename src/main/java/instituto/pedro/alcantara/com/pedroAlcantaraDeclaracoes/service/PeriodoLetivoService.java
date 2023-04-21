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
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.InstituicaoRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.PeriodoLetivoRepository;

@Service
public class PeriodoLetivoService {
    
    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    public PeriodoLetivo save(@Valid PeriodoLetivoDTO p) throws Exception {
        PeriodoLetivo periodo = new PeriodoLetivo();
        Instituicao i = instituicaoRepository.findById(p.getInstituicao())
            .orElseThrow(() -> new Exception("Deu ruim asoidasnoiads"));
        List<PeriodoLetivo> periodosInstituicao = i.getPeriodos();
        LocalDate dataInicio = LocalDate.parse(p.getDataInicio(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate dataFinal = LocalDate.parse(p.getDataFinal(), DateTimeFormatter.ISO_LOCAL_DATE);
        periodo.setAno(p.getAno());
        periodo.setPeriodo(p.getPeriodo());
        periodo.setDataInicio(dataInicio);
        periodo.setDataFinal(dataFinal);
        periodo.setInstituicao(i);
        periodosInstituicao.add(periodo);
        i.setPeriodos(periodosInstituicao);
        i.setPeriodoAtual(periodo);
        return periodoLetivoRepository.save(periodo);
    }


    public List<PeriodoLetivo> getAll(){
        return this.periodoLetivoRepository.findAll();
    }


}
