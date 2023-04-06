package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.InstituicaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.InstituicaoRepository;

@Service

public class InstituicaoService{
    
    @Autowired
    private InstituicaoRepository instituicaoRepository;

    public List<Instituicao> getAll() {
        return this.instituicaoRepository.findAll();
    }

    public Optional<Instituicao> getAllAlunosByInstituicao(Integer id){
        return this.instituicaoRepository.findInstituicaoFetchAlunos(id);
    }

    @Transactional
    public Instituicao save(InstituicaoDTO i) {
        Instituicao novaInstituicao = new Instituicao();
        novaInstituicao.setNome(i.getNome());
        novaInstituicao.setSigla(i.getSigla());
        novaInstituicao.setFone(i.getFone());
        novaInstituicao.setAlunos(this.converterA(i.getAlunos()));
        
        return this.instituicaoRepository.save(novaInstituicao);
    }

    private List<Estudante> converterA(List<EstudanteDTO> alunos){
        return alunos == null ? new ArrayList<Estudante>() : alunos.stream().map(
            item -> {
                Estudante e = new Estudante();
                Instituicao i = null;
                try {
                    i = this.instituicaoRepository
                        .findById(item.getInstituicaoAtual())
                        .orElseThrow(() -> new Exception("asjisadji"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.setMatricula(item.getMatricula());
                e.setNome(item.getNome());
                e.setInstituicaoAtual(i);
                
                return e;
                
            }).collect(Collectors.toList());
    }


}
