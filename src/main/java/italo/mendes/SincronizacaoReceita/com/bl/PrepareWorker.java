package italo.mendes.SincronizacaoReceita.com.bl;

import italo.mendes.SincronizacaoReceita.ApplicationConstants;
import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.dto.ArquivoRetaguardaUtils;
import italo.mendes.SincronizacaoReceita.com.dto.ArquivoRetaguarda;
import com.google.common.collect.Lists;
import italo.mendes.SincronizacaoReceita.com.merge.MergeWorkerFactory;
import italo.mendes.SincronizacaoReceita.com.worker.Worker;
import italo.mendes.SincronizacaoReceita.com.worker.WorkerFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Classe responsável por dividir o arquivo de forma balanceada e enviar para a classe Worker.
 *
 * @author Italo Mendes Rodrigues
 */
@Component
@Getter
@Setter
public class PrepareWorker {

    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    WorkerFactory workerFactory;
    @Autowired
    MergeWorkerFactory mergeFactory;

    private ArquivoRetaguardaUtils arquivoUtils;

    public void prepareRoutine(File arquivoRetaguardaCSV){
        try {
            arquivoUtils = new ArquivoRetaguardaUtils();
            List<ArquivoRetaguarda> linhas = arquivoUtils.loadArquivoRetaguarda(arquivoRetaguardaCSV, 1,';');
            int workerId = 0;
            for (List<ArquivoRetaguarda> linhasPerWorker : Lists.partition(linhas, applicationProperties.getMaxItemsPerWorker())) {
                workerFactory.getWorkers().add(new Worker(applicationProperties, linhasPerWorker, workerId++));
            }
            System.out.printf(ApplicationConstants.MESSAGE_WORKER_FACTORY_LOAD, applicationProperties.getApplicationName(),
                    workerFactory.getWorkers().size(), linhas.size());
            arquivoUtils.cleanTempFolder(applicationProperties);
            workerFactory.process();
            mergeFactory.process();

        } catch (Exception e) {
            System.out.printf( ApplicationConstants.MESSAGE_PREPARE_WORKER_ERROR, applicationProperties.getApplicationName(),
                    arquivoRetaguardaCSV.getName(), e.getMessage());
        }
    }
}


