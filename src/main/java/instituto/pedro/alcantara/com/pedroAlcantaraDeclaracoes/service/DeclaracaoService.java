package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.DeclaracaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.DeclaracaoRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.InstituicaoRepository;

@Service

public class DeclaracaoService {

    @Autowired
    private DeclaracaoRepository DeclaracaoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Transactional
    public Declaracao cadastrar(Declaracao declaracao) throws Exception {
        return this.DeclaracaoRepository.save(declaracao);
    }

    public List<Declaracao> getAll() {
        return this.DeclaracaoRepository.findAll();
    }

    public Page<Declaracao> getAllToList(Pageable pageable) {
        return this.DeclaracaoRepository.findAll(pageable);
    }

}
