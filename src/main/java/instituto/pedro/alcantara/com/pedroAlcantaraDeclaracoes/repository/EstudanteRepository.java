package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.Estudante;

public interface EstudanteRepository extends JpaRepository<Estudante, Integer> {

    Optional<Estudante> findById(Integer id);

    List<Estudante> findAll();


}
