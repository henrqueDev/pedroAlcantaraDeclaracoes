package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DeclaracaoDTO {
    private Integer id;

    @NotBlank(message = "Campo obrigatório!")
    private String observacao;

    private String dataRecebimento;

    @NotNull(message = "Precisa de um estudante!")
    private Integer estudante;

    @NotNull(message = "Campo obrigatório!")
    private Integer periodo;
}
