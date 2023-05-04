package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;

public interface PeriodoLetivoRepository extends JpaRepository<PeriodoLetivo, Integer> {

    @Query("select p from PeriodoLetivo p where p.dataFinal < :data order by p.dataFinal desc")
    Optional<PeriodoLetivo> findByLastPeriodo(@Param(value = "data") LocalDate data);

    @Modifying
    @Query("update PeriodoLetivo p set p.ano = :ano, p.periodo = :periodo, p.dataInicio = :dataInicio, p.dataFinal = :dataFinal where p.id = :id")
    void update(@Param(value = "id") Integer id, @Param(value = "ano") int ano, @Param(value = "periodo") int periodo,
            @Param(value = "dataInicio") LocalDate dataInicio, @Param(value = "dataFinal") LocalDate dataFinal);

}
