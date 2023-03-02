package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model;

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name="estudantes")

public class Estudante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="matricula")
    private Integer matricula;

    @Column(name="nome")
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "declaracao_fk", referencedColumnName = "id")
    private Declaracao declaracaoAtual;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="instituicao_fk", referencedColumnName = "id")
    private Instituicao instituicaoAtual;

    @ManyToOne
    private Instituicao instituicao;

}
