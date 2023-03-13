package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.InstituicaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.InstituicaoRepository;

@Service

public class InstituicaoService{
    
    @Autowired
    private InstituicaoRepository instituicaoRepository;

    public List<Instituicao> getAll() {
        return this.instituicaoRepository.findAll();
    }

    @Transactional
    public Instituicao save(InstituicaoDTO i) {
        Instituicao novaInstituicao = new Instituicao();
        novaInstituicao.setNome(i.getNome());
        novaInstituicao.setSigla(i.getSigla());
        novaInstituicao.setFone(i.getFone());
        novaInstituicao.setAlunos(i.getAlunos());
        return this.instituicaoRepository.save(novaInstituicao);
    }
}
