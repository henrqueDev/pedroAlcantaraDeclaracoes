package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PeriodoLetivoDTO {
    private Integer id;

    private int ano;

    private int periodo;

    @NotBlank(message = "Campo obrigatório!")
    private String dataInicio;

    @NotBlank(message = "Campo obrigatório!")
    private String dataFinal;

    private List<DeclaracaoDTO> declaracoes;

    @NotNull(message = "Precisa de uma Instituição!")
    private Integer instituicao;

}
