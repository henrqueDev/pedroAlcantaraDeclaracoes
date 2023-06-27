package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.builder.EstudanteBuilder;
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

    public Page<Instituicao> getAll(Pageable pageable) {
        return this.instituicaoRepository.findAll(pageable);
    }

    public Page<Instituicao> getAllPaginated(Integer page, int PAGE_SIZE) {

        Pageable pageable = PageRequest.of(page != null ? page : 0, PAGE_SIZE);
        List<Instituicao> instituicoesDTO = this.instituicaoRepository.findAll();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), instituicoesDTO.size());
        Page<Instituicao> pagina = new PageImpl<>(instituicoesDTO.subList(start, end), pageable,
                instituicoesDTO.size());
        return pagina;
    }

    public List<Instituicao> getAllWithoutPagination() {
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
        novaInstituicao.setAlunos(EstudanteBuilder.desconstructToDTOList(i.getAlunos(), this.instituicaoRepository));

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

}
