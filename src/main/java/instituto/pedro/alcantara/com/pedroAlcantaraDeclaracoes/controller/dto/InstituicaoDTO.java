package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

public class InstituicaoDTO {

    private Integer id;

    @NotBlank(message = "Campo obrigatório!")
    private String nome;

    @NotBlank(message = "Campo obrigatório!")
    private String sigla;

    @NotBlank(message = "Campo obrigatório!")
    private String fone;

    private List<EstudanteDTO> alunos;
}
