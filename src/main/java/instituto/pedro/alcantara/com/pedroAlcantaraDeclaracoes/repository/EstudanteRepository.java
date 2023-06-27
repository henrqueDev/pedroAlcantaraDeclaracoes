package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;

public interface EstudanteRepository extends JpaRepository<Estudante, Integer> {

    Page<Estudante> findAll(Pageable pageable);

    Optional<Estudante> findById(Integer id);

    List<Estudante> findAllByInstituicaoAtual(Instituicao instituicaoAtual);

    @Query("select e from Estudante e where e.declaracaoAtual.periodo = :periodo ")
    List<Estudante> findAllEstudantesByPeriodo(@Param(value = "periodo") PeriodoLetivo periodo);

    @Query(value = "select e from Estudante e where e.declaracaoAtual = NULL ", countQuery = "select count(*) from Estudante e where e.declaracaoAtual = null ")
    Page<Estudante> findAllEstudantesWithoutDeclaracao(Pageable pageable);

    @Modifying
    @Query("update Estudante e set e.nome = :nome, e.instituicaoAtual = :instituicaoAtual where e.matricula = :matricula")
    void updateEstudante(@Param(value = "matricula") Integer matricula, @Param(value = "nome") String nome,
            @Param(value = "instituicaoAtual") Instituicao instituicaoAtual);

    @Modifying
    @Query("delete from Estudante e where e.matricula = :matricula")
    void deleteEstudanteByMatricula(@Param(value = "matricula") Integer matricula);

}
