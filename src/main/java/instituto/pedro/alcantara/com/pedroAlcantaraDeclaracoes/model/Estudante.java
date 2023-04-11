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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name="estudantes")

public class Estudante {

    public Estudante(Instituicao instituicao, String nome, Declaracao declaracao){
        this.instituicaoAtual = instituicao;
        this.nome = nome;
        this.declaracaoAtual = declaracao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="matricula")
    private Integer matricula;

    @Column(name="nome")
    private String nome;

    @OneToOne(mappedBy = "estudante")
    private Declaracao declaracaoAtual;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="instituicao_id")
    private Instituicao instituicaoAtual;

}
