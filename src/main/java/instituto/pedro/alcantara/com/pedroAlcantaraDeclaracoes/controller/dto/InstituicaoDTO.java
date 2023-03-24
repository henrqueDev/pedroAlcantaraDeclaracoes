package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InstituicaoDTO {
    private String nome;
    private String sigla;
    private String fone;
    private List<EstudanteDTO> alunos;
}
