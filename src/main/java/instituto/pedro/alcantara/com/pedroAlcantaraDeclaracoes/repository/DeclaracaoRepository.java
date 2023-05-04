package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Declaracao;

public interface DeclaracaoRepository extends JpaRepository<Declaracao, Integer> {

    @Modifying
    @Query("delete from Declaracao d where d.id=:id")
    void deleteDeclaracaoByid(@Param(value = "id") Integer id);

}
