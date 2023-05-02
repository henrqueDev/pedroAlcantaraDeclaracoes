package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;

public interface PeriodoLetivoRepository extends JpaRepository<PeriodoLetivo, Integer> {

    @Query("select p from PeriodoLetivo p order by p.dataFinal desc")
    Optional<PeriodoLetivo> findByLastPeriodo();

}
