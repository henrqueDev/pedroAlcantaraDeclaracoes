package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model;

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

@Table(name="declaracoes")


public class Declaracao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="observacao")
    private String observacao;

    @Column(name="dataRecebimento")
    @NotNull
    private LocalDateTime dataRecebimento;

    @OneToOne(mappedBy = "declaracaoAtual")
    private Estudante estudante;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="estudante_fk", referencedColumnName = "matricula")
    private Estudante estudanteId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="periodo_fk", referencedColumnName = "id")
    private PeriodoLetivo periodo;

}
