package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;


import java.time.LocalDate;

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
    private String observacao;
    private LocalDate dataRecebimento;
    private Integer estudante;
}
