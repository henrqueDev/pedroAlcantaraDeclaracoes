package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.DeclaracaoRepository;

@Service

public class DeclaracaoService {

    @Autowired
    private DeclaracaoRepository DeclaracaoRepository;

    @Transactional
    public Declaracao cadastrar(Declaracao declaracao) throws Exception {
        return this.DeclaracaoRepository.save(declaracao);
    }

    public List<Declaracao> getAll() {
        return this.DeclaracaoRepository.findAll();
    }

    public Page<Declaracao> getAllVencidas(Pageable pageable) {
        return this.DeclaracaoRepository.getAllDeclaracoesVencidas(pageable, LocalDate.now());
    }

    public Page<Declaracao> getAllVencerDias(Integer numLimite, Pageable pageable) {
        LocalDate data = LocalDate.now();
        return this.DeclaracaoRepository.getAllDeclaracoesVencerDias(pageable, data, data.plusDays(numLimite));
    }

    public Page<Declaracao> getAllToList(Pageable pageable) {
        return this.DeclaracaoRepository.findAll(pageable);
    }

}
