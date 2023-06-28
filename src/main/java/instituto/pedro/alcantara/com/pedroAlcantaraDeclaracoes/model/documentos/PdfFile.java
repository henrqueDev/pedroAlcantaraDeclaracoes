package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.documentos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pdfFiles")
public class PdfFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Campo obrigatório!")
    private String nome;

    @Lob
    private byte[] bytes;

    public PdfFile(String nome, byte[] bytes) {
        this.nome = nome;
        this.bytes = bytes;
    }
}
