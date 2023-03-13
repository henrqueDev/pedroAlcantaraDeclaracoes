package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

@Table(name="instituicoes")

public class Instituicao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name="nome")
    @NotNull
    private String nome;

    @Column(name="sigla")
    @NotNull
    private String sigla;

    @Column(name="fone")
    @NotNull
    private String fone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="periodoAtual_fk", referencedColumnName = "id")
    private PeriodoLetivo periodoAtual;

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
    private Set<PeriodoLetivo> periodos;

    @OneToMany(mappedBy = "instituicaoAtual", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Estudante> alunos;


}
