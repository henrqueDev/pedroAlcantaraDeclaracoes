package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;

public interface EstudanteRepository extends JpaRepository<Estudante, Integer> {

    List<Estudante> findAll();

    @Modifying
    @Query("update Estudante e set e.nome = :nome, e.instituicaoAtual = :instituicaoAtual, e.declaracaoAtual = null where e.matricula = :matricula")
    void updateEstudante(@Param(value = "matricula") Integer matricula, @Param(value = "nome") String nome,
            @Param(value = "instituicaoAtual") Instituicao instituicaoAtual);

}
