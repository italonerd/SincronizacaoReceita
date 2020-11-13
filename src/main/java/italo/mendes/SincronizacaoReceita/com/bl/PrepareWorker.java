package italo.mendes.SincronizacaoReceita.com.bl;

import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.dto.LinhaArquivoRetaguardaUtils;
import italo.mendes.SincronizacaoReceita.com.dto.LinhaArquivoRetaguarda;
import com.google.common.collect.Lists;
import italo.mendes.SincronizacaoReceita.com.merge.MergeWorkerFactory;
import italo.mendes.SincronizacaoReceita.com.worker.Worker;
import italo.mendes.SincronizacaoReceita.com.worker.WorkerFactory;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Classe responsável por dividir o arquivo de forma balanceada e enviar para a classe Worker.
 *
 * @author Italo Mendes Rodrigues
 */

@Component
public class PrepareWorker {

    //TODO Ajustar para adicionar injeção de código
    private ApplicationProperties applicationProperties;
    private LinhaArquivoRetaguardaUtils arquivoUtils;

    public PrepareWorker(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
        this.arquivoUtils = new LinhaArquivoRetaguardaUtils();
    }

    public void prepareRoutine(File arquivoRetaguardaCSV){
        try {
            List<LinhaArquivoRetaguarda> linhas = arquivoUtils.carregarLinhasArquivoRetaguardaCSV(arquivoRetaguardaCSV, 1,';');
            WorkerFactory factory =  new WorkerFactory(applicationProperties);
            int workerId = 0;
            for (List<LinhaArquivoRetaguarda> linhasPerWorker : Lists.partition(linhas, applicationProperties.getMaxItemsPerWorker())) {
                factory.getWorkers().add(new Worker(applicationProperties, linhasPerWorker, workerId++));
            }
            System.out.println(applicationProperties.getApplicationName()+" - "+factory.getWorkers().size()+" Trabalhadores vão processar as "+linhas.size()+" linhas do arquivo informado.");
            arquivoUtils.cleanTempFolder(applicationProperties);
            factory.process();

            MergeWorkerFactory mergeFactory =  new MergeWorkerFactory(applicationProperties);
            mergeFactory.process();

        } catch (Exception e) {
            System.out.println(applicationProperties.getApplicationName()+" - Erro ao preparar o processamento do arquivo("+arquivoRetaguardaCSV.getName()+"). Motivo:"+e.getMessage());
        }
    }
}


