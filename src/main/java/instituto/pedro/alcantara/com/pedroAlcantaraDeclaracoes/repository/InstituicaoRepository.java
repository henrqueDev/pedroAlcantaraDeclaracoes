package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer> {

    Optional<Instituicao> findById(Integer id);

    Page<Instituicao> findAll(Pageable pageable);

    @Modifying
    @Query("update Instituicao i set i.nome = :nome, i.sigla = :sigla, i.fone = :fone, i.periodoAtual = :periodoAtual where i.id = :id")
    void updateInstituicao(@Param(value = "id") Integer id, @Param(value = "nome") String nome,
            @Param(value = "sigla") String sigla, @Param(value = "fone") String fone,
            @Param(value = "periodoAtual") PeriodoLetivo periodoAtual);

    @Modifying
    @Query("delete from Instituicao i where i.id = :id")
    void deleteInstituicaoById(@Param(value = "id") Integer id);

}
