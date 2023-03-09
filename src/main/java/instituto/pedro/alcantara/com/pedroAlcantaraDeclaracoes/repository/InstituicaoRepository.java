package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer> {
    
    Optional<Instituicao> findById(Integer id);

    List<Instituicao> findAll();

    @Query(" select i from instituicoes i left join fetch i.alunos where i.id = :id ")
    Instituicao findInstituicaoFetchAlunos(@Param("id") Integer id);

}
