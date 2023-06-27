package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;

public interface DeclaracaoRepository extends JpaRepository<Declaracao, Integer> {

        @Modifying
        @Query("delete from Declaracao d where d.id=:id")
        void deleteDeclaracaoByid(@Param(value = "id") Integer id);

        @Query(value = "select d from Declaracao d where d.periodo.dataFinal < :endDate", countQuery = "select count(*) from Declaracao d where d.periodo.dataFinal < :endDate ")
        public Page<Declaracao> getAllDeclaracoesVencidas(Pageable pageable,
                        @Param("endDate") LocalDate endDate);

        @Query(value = "select d from Declaracao d where d.periodo.dataFinal between :startDate and :endDate", countQuery = "select count(*) from Declaracao d where d.periodo.dataFinal between :startDate and :endDate")
        public Page<Declaracao> getAllDeclaracoesVencerDias(Pageable pageable,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

}
