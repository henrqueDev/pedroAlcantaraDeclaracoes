package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PeriodoLetivoDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int ano;
    private int periodo;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private List<DeclaracaoDTO> declaracoes;
    private InstituicaoDTO instituicao;

}
