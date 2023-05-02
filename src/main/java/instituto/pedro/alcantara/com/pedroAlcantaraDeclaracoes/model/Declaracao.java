package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

    public Declaracao(String observacao, LocalDate dataRecebimento, Estudante estudante) {
        this.observacao = observacao;
        this.dataRecebimento = dataRecebimento;
        this.estudante = estudante;
        this.periodo = estudante.getInstituicaoAtual().getPeriodoAtual();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "dataRecebimento")
    @NotNull
    private LocalDate dataRecebimento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estudante_fk")
    private Estudante estudante;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "periodo_fk", referencedColumnName = "id")
    private PeriodoLetivo periodo;

    public String toString() {
        return this.id.toString();
    }

}
