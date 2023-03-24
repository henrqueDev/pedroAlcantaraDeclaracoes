package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

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
