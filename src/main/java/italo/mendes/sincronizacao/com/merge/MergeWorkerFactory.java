package italo.mendes.sincronizacao.com.merge;

import italo.mendes.sincronizacao.ApplicationConstants;
import italo.mendes.sincronizacao.ApplicationProperties;
import italo.mendes.sincronizacao.com.dto.ArquivoRetaguardaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Classe responsável por controlar instancias da classe MergeWorker.
 *
 * @author Italo Mendes Rodrigues
 */
@Slf4j
@Component
public class MergeWorkerFactory {
    @Autowired
    private ApplicationProperties applicationProperties;
    private List<MergeWorker> workers;
    private ArquivoRetaguardaUtils utils;

    public MergeWorkerFactory() {
        this.workers = new ArrayList<>();
        this.utils = new ArquivoRetaguardaUtils();
    }

    public void process() throws Exception {
        log.info(ApplicationConstants.MESSAGE_MERGE_WORKER_FACTORY_START);

        createMergeWorkers();
        ExecutorService workersExecutor = Executors.newFixedThreadPool(applicationProperties.getNumberOfWorkers());
        for (MergeWorker woker : workers) {
            workersExecutor.execute(woker);
        }
        workersExecutor.shutdown();
        try {
            workersExecutor.awaitTermination(applicationProperties.getMaxTimeToProcess(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error(String.format(ApplicationConstants.MESSAGE_MERGE_WORKER_FACTORY_ERROR, e.getMessage()));
        }
    }

    private void createMergeWorkers() throws Exception {
        utils.generateFolder(new File(applicationProperties.getPathTemp()));
        for (int i = 0; i < this.applicationProperties.getNumberOfMergeWorkers(); i++) {
            MergeWorker worker = new MergeWorker(applicationProperties, i);
            this.workers.add(worker);
        }
    }
}
