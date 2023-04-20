package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;


import java.time.LocalDate;

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

public class DeclaracaoDTO {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String observacao;
    private LocalDate dataRecebimento;
    private Integer estudante;
}
