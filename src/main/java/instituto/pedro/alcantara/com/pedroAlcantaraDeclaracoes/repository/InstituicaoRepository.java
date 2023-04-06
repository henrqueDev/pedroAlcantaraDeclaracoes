package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Instituicao;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer> {
    
    Optional<Instituicao> findById(Integer id);

    List<Instituicao> findAll();
}
