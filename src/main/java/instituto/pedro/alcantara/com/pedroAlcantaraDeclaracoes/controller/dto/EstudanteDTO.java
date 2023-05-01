package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.repository.NoRepositoryBean;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EstudanteDTO {
    private Integer matricula;

    @NotBlank(message = "Campo é obrigatório!")
    @NotNull
    @NotEmpty(message = "obrigatorio")
    private String nome;

    private Integer instituicaoAtual;
    private Integer declaracaoAtual;

}
