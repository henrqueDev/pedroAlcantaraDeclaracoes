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
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao.InstituicaoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.periodo.PeriodoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.InstituicaoRepository;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.PeriodoLetivoRepository;

@Service

public class InstituicaoService {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;

    public List<Instituicao> getAll() {
        return this.instituicaoRepository.findAll();
    }

    public Instituicao getById(Integer id) throws Exception {
        return this.instituicaoRepository.findById(id).orElseThrow(() -> new InstituicaoNotFoundException());
    }

    public List<Estudante> getAllEstudantesById(Integer id) {
        Instituicao i = this.instituicaoRepository.findById(id).orElseThrow(() -> new InstituicaoNotFoundException());
        return i.getAlunos();
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

    @Transactional
    public void update(InstituicaoDTO i) {
        Instituicao instituicaoAtualizada = i.getId() != null ? this.instituicaoRepository.findById(i.getId())
                .orElseThrow(() -> new InstituicaoNotFoundException()) : null;
        PeriodoLetivo newPeriodo = i.getPeriodoAtual() != null
                ? this.periodoLetivoRepository.findById(i.getPeriodoAtual())
                        .orElseThrow(() -> new PeriodoNotFoundException())
                : null;
        /*
         * if (newPeriodo == null) {
         * for (Estudante e : instituicaoAtualizada.getAlunos()) {
         * e.setDeclaracaoAtual(null);
         * }
         * }
         */
        instituicaoAtualizada.setNome(i.getNome());
        instituicaoAtualizada.setSigla(i.getSigla());
        instituicaoAtualizada.setFone(i.getFone());
        instituicaoAtualizada.setPeriodoAtual(newPeriodo);
        this.instituicaoRepository.updateInstituicao(instituicaoAtualizada.getId(), instituicaoAtualizada.getNome(),
                instituicaoAtualizada.getSigla(),
                instituicaoAtualizada.getFone(), instituicaoAtualizada.getPeriodoAtual());
    }

    @Transactional
    public void deleteInstituicao(Integer instituicao) {
        Instituicao i = this.instituicaoRepository.findById(instituicao)
                .orElseThrow(() -> new InstituicaoNotFoundException());
        List<Estudante> estudantes = i.getAlunos();
        for (Estudante estudante : estudantes) {
            estudante.setInstituicaoAtual(null);
            estudante.setDeclaracaoAtual(null);
        }
        this.instituicaoRepository.delete(i);
    }

    private List<Estudante> converterA(List<EstudanteDTO> alunos) {
        return alunos == null ? new ArrayList<Estudante>()
                : alunos.stream().map(
                        item -> {
                            Estudante e = new Estudante();
                            Instituicao i = null;
                            try {
                                i = this.instituicaoRepository
                                        .findById(item.getInstituicaoAtual())
                                        .orElseThrow(() -> new InstituicaoNotFoundException());
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
