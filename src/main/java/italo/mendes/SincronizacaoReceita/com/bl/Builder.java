package italo.mendes.SincronizacaoReceita.com.bl;

import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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
