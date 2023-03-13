package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EstudanteDTO {
    private String nome;
    private Integer instituicaoAtual;
}
