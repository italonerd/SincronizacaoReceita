package italo.mendes.sincronizacao.com.bl;

import italo.mendes.sincronizacao.ApplicationConstants;
import italo.mendes.sincronizacao.ApplicationProperties;
import italo.mendes.sincronizacao.com.dto.ArquivoRetaguardaUtils;
import italo.mendes.sincronizacao.com.dto.ArquivoRetaguarda;
import com.google.common.collect.Lists;
import italo.mendes.sincronizacao.com.merge.MergeWorkerFactory;
import italo.mendes.sincronizacao.com.worker.Worker;
import italo.mendes.sincronizacao.com.worker.WorkerFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            List<ArquivoRetaguarda> linhas = arquivoUtils.loadArquivoRetaguarda(arquivoRetaguardaCSV, 1, ';');
            int workerId = 0;
            for (List<ArquivoRetaguarda> linhasPerWorker : Lists.partition(linhas, applicationProperties.getMaxItemsPerWorker())) {
                workerFactory.getWorkers().add(new Worker(applicationProperties, linhasPerWorker, workerId++));
            }
            log.info(String.format(ApplicationConstants.MESSAGE_WORKER_FACTORY_LOAD, workerFactory.getWorkers().size(), linhas.size()));
            arquivoUtils.cleanTempFolder(applicationProperties.getPathTemp());
            workerFactory.process();
            mergeFactory.process();

        } catch (Exception e) {
            log.error(String.format(ApplicationConstants.MESSAGE_PREPARE_WORKER_ERROR, arquivoRetaguardaCSV.getName(), e.getMessage()));
        }
    }
}


