package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao.InstituicaoNotFoundException;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.InstituicaoRepository;

public final class EstudanteBuilder {

    public static EstudanteDTO convertToDTO(Estudante e) {
        Integer instituicao = e.getInstituicaoAtual() != null ? e.getInstituicaoAtual().getId() : null;
        Integer declaracao = e.getDeclaracaoAtual() != null ? e.getDeclaracaoAtual().getId() : null;
        return EstudanteDTO.builder()
                .matricula(e.getMatricula())
                .nome(e.getNome())
                .instituicaoAtual(instituicao)
                .declaracaoAtual(declaracao)
                .build();

    }

    public static List<EstudanteDTO> convertToListEstudanteDTO(List<Estudante> e) {
        return e.stream().map(estudante -> {
            return EstudanteDTO
                    .builder()
                    .matricula(estudante.getMatricula())
                    .nome(estudante.getNome())
                    .instituicaoAtual(estudante.getInstituicaoAtual().getId())
                    .build();
        }).collect(Collectors.toList());

    }

    public static List<Estudante> desconstructToDTOList(List<EstudanteDTO> alunos,
            InstituicaoRepository instituicaoRepository) {
        return alunos == null ? new ArrayList<Estudante>()
                : alunos.stream().map(
                        item -> {
                            Estudante e = new Estudante();
                            Instituicao i = null;
                            try {
                                i = instituicaoRepository
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
