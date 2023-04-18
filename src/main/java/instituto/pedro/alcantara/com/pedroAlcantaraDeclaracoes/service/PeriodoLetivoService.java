package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

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

    public void save(@Valid PeriodoLetivo p) throws Exception {
        periodoLetivoRepository.save(p);
        Instituicao i = instituicaoRepository.findById(p.getInstituicao().getId())
            .orElseThrow(() -> new Exception("Deu ruim asoidasnoiads"));
        List<PeriodoLetivo> periodosInstituicao = i.getPeriodos();
        periodosInstituicao.add(p);
        i.setPeriodos(periodosInstituicao);
        i.setPeriodoAtual(p);
    }

    public Collection<PeriodoLetivoDTO> getAll() {
        return null;
    }
   


}
