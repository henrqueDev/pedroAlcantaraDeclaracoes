package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

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

    @NotBlank
    private String nome;

    @NotBlank
    private String sigla;

    @NotBlank
    private String fone;

    private List<EstudanteDTO> alunos;
}
