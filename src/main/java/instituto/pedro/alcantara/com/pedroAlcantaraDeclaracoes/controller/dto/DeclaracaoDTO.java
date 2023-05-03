package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;
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
