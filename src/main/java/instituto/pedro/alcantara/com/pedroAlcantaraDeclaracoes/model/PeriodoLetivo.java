package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name="periodoLetivo")

public class PeriodoLetivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="ano")
    @NotNull
    private int ano;

    @Column(name="periodo")
    @NotNull
    private int periodo;

    @Column(name="dataInicio")
    @NotNull
    private LocalDateTime dataInicio;

    @Column(name="dataFinal")
    @NotNull
    private LocalDateTime dataFinal;

    @OneToMany(mappedBy = "periodo")
    private Set<Declaracao> declaracao;

    @ManyToOne
    private Instituicao instituicao;

}
