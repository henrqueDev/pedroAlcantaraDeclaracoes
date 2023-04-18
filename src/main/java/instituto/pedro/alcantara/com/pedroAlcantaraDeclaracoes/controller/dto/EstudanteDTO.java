package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EstudanteDTO {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer matricula;
    
    private String nome;
    private Integer instituicaoAtual;
    private DeclaracaoDTO declaracaoAtual;
}
