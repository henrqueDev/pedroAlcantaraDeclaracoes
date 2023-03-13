package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import java.util.Set;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class InstituicaoDTO {
    private String nome;
    private String sigla;
    private String fone;
    private Set<Estudante> alunos;
}
