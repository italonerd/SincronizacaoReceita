package italo.mendes.SincronizacaoReceita.com.builder;

import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.worker.PrepareWorker;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Classe responsável por construir a rotina de processamento de arquivos.
 *
 * @author Italo Mendes Rodrigues
 */

@Component
@NoArgsConstructor
public class Builder {

    //TODO Ajustar para adicionar injeção de código
    private ApplicationProperties applicationProperties;
    private File arquivoRetaguardaCSV;

    public Builder(ApplicationProperties applicationProperties, File arquivoRetaguardaCSV){
        this.applicationProperties = applicationProperties;
        this.arquivoRetaguardaCSV = arquivoRetaguardaCSV;
    }

    public void build(){
        PrepareWorker prepare = new PrepareWorker(this.applicationProperties);
        prepare.prepareRoutine(this.arquivoRetaguardaCSV);
    }
}
