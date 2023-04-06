package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.repository.PeriodoLetivoRepository;

@Service
public class PeriodoLetivoService {
    
    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;
   


}
