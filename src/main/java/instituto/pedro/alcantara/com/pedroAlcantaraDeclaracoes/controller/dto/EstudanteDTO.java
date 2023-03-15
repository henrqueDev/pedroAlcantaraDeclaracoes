package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EstudanteDTO {
    private Integer matricula;
    private String nome;
    private Integer instituicaoAtual;
}
