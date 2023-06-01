package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.DeclaracaoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "declaracoes")

public class Declaracao {

    public Declaracao(String observacao, LocalDate dataRecebimento, Estudante estudante, PeriodoLetivo periodo) {
        this.observacao = observacao;
        this.dataRecebimento = dataRecebimento;
        this.estudante = estudante;
        this.periodo = periodo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "observacao")
    @NotBlank
    private String observacao;

    @Column(name = "dataRecebimento")
    @NotNull
    private LocalDate dataRecebimento;

    @ManyToOne
    @JoinColumn(name = "estudante_fk", referencedColumnName = "matricula")
    private Estudante estudante;

    @ManyToOne
    @JoinColumn(name = "periodo_fk")
    private PeriodoLetivo periodo;

    public String toString() {
        return this.id.toString();
    }

    public static DeclaracaoDTO converterDeclaracaoDTO(Declaracao declaracao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

        return DeclaracaoDTO
                .builder()
                .id(declaracao.getId())
                .observacao(declaracao.getObservacao())
                .dataRecebimento(declaracao.getDataRecebimento().format(formatter))
                .estudante(declaracao.getEstudante().getMatricula())
                .periodo(declaracao.getPeriodo().getId())
                .build();
    }

}
