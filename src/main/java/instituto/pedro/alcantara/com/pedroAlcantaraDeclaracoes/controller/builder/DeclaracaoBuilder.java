package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.builder;

import java.time.format.DateTimeFormatter;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.DeclaracaoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;

public final class DeclaracaoBuilder {

    public static DeclaracaoDTO converterDeclaracaoDTO(Declaracao declaracao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

        return DeclaracaoDTO
                .builder()
                .id(declaracao.getId())
                .observacao(declaracao.getObservacao())
                .dataRecebimento(declaracao.getDataRecebimento().format(formatter))
                .estudante(declaracao.getEstudante().getMatricula())
                .periodo(declaracao.getPeriodo().getId())
                .build();
    }

}
