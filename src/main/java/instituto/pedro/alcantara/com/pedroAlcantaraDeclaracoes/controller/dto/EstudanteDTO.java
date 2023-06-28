package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import javax.validation.constraints.NotBlank;

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

    @NotBlank(message = "Campo é obrigatório!")
    private String nome;

    private Integer instituicaoAtual;
    private Integer declaracaoAtual;

}
