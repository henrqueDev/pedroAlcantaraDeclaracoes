package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.EstudanteDTO;
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

@Table(name = "estudantes")

public class Estudante {

    public Estudante(Instituicao instituicao, String nome, Declaracao declaracao) {
        this.instituicaoAtual = instituicao;
        this.nome = nome;
        this.declaracaoAtual = declaracao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "matricula")
    private Integer matricula;

    @Column(name = "nome")
    @NotBlank(message = "Campo obrigatório!")
    private String nome;

    @OneToMany(mappedBy = "estudante", cascade = CascadeType.ALL)
    private List<Declaracao> declaracoes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "declaracao_atual")
    private Declaracao declaracaoAtual;

    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicaoAtual;

    public static EstudanteDTO converterEstudanteDTO(Estudante i) {
        Integer instituicao = i.getInstituicaoAtual() != null ? i.getInstituicaoAtual().getId() : null;
        Integer declaracao = i.getDeclaracaoAtual() != null ? i.getDeclaracaoAtual().getId() : null;
        return EstudanteDTO.builder()
                .matricula(i.getMatricula())
                .nome(i.getNome())
                .instituicaoAtual(instituicao)
                .declaracaoAtual(declaracao)
                .build();
    }

    public static List<EstudanteDTO> converterEstudanteDTO(List<Estudante> e) {
        return e.stream().map(estudante -> {
            return EstudanteDTO
                    .builder()
                    .matricula(estudante.getMatricula())
                    .nome(estudante.getNome())
                    .instituicaoAtual(estudante.getInstituicaoAtual().getId())
                    .build();
        }).collect(Collectors.toList());

    }

}
