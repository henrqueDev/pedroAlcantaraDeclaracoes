package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.documentos.PdfFile;

public interface PdfFileRepository extends JpaRepository<PdfFile, Integer> {

}
