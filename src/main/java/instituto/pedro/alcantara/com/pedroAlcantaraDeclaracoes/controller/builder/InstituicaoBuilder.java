package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.builder;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.InstituicaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;

public final class InstituicaoBuilder {

    public static InstituicaoDTO convertToDTO(Instituicao i) {

        Integer periodo = i.getPeriodoAtual() != null ? i.getPeriodoAtual().getId() : null;

        return InstituicaoDTO.builder()
                .id(i.getId())
                .nome(i.getNome())
                .sigla(i.getSigla())
                .fone(i.getFone())
                .alunos(EstudanteBuilder.convertToListEstudanteDTO(i.getAlunos()))
                .periodoAtual(periodo)
                .build();

    }

}
