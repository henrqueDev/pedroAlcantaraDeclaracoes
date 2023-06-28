package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.builder;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.controller.dto.PeriodoLetivoDTO;
import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.model.PeriodoLetivo;

public final class PeriodoLetivoBuilder {

    public static PeriodoLetivoDTO convertToDTO(PeriodoLetivo p) {
        Integer i = p.getInstituicao() != null ? p.getInstituicao().getId() : null;
        return PeriodoLetivoDTO.builder()
                .id(p.getId())
                .ano(p.getAno())
                .periodo(p.getPeriodo())
                .dataInicio(p.getDataInicio().toString())
                .dataFinal(p.getDataFinal().toString())
                .instituicao(i)
                .build();
    }

}
