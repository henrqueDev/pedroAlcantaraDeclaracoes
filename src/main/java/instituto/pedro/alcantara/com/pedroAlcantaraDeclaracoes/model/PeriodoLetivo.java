package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDate dataInicio;

    @Column(name="dataFinal")
    @NotNull
    private LocalDate dataFinal;

    @OneToMany(mappedBy = "periodo")
    private List<Declaracao> declaracao;

    @ManyToOne
    private Instituicao instituicao;

}
