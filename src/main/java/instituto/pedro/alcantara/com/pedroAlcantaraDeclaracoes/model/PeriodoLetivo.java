package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Table(name = "periodoLetivo")

public class PeriodoLetivo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ano")
    @NotNull
    private int ano;

    @Column(name = "periodo")
    @NotNull
    private int periodo;

    @Column(name = "dataInicio")
    @NotNull
    private LocalDate dataInicio;

    @Column(name = "dataFinal")
    @NotNull
    private LocalDate dataFinal;
    // 1 - 30/1 to 30/06 2- 01/07 to 31/12
    // 31/1 to 30/07

    @OneToMany(mappedBy = "periodo", cascade = CascadeType.ALL)
    private List<Declaracao> declaracoes;

    @ManyToOne
    private Instituicao instituicao;

    public boolean checkLastPeriodoData(PeriodoLetivo p) {
        return this.dataInicio.isAfter(p.getDataFinal()) && this.dataFinal.isAfter(p.getDataFinal()) ? true : false;
    }

    public String toString() {
        return this.dataInicio.getYear() + "." + this.getPeriodo();
    }

}
