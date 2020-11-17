package italo.mendes.SincronizacaoReceita.com.merge;

import italo.mendes.SincronizacaoReceita.ApplicationConstants;
import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.worker.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class MergeWorkerFactory {
    @Autowired
    private ApplicationProperties applicationProperties;
    private List<MergeWorker> workers;

    public MergeWorkerFactory() {
        this.applicationProperties = new ApplicationProperties();
        this.workers = new ArrayList<MergeWorker>();
    }

    public void process() {
        System.out.printf(ApplicationConstants.MESSAGE_MERGE_WORKER_FACTORY_START, applicationProperties.getApplicationName());

        createMergeWorkers();
        ExecutorService workersExecutor = Executors.newFixedThreadPool(applicationProperties.getNumberOfWorkers());
        for (MergeWorker woker : workers) {
            workersExecutor.execute(woker);
        }
        workersExecutor.shutdown();
        try {
            workersExecutor.awaitTermination(applicationProperties.getMaxTimeToProcess(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.printf(ApplicationConstants.MESSAGE_MERGE_WORKER_FACTORY_ERROR, applicationProperties.getApplicationName(), e.getMessage());
        }
    }

    private void createMergeWorkers() {
        for (int i = 0; i < this.applicationProperties.getNumberOfMergeWorkers(); i++) {
            MergeWorker worker = new MergeWorker(applicationProperties, i);
            this.workers.add(worker);
        }
    }
}
