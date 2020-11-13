package italo.mendes.SincronizacaoReceita.com.merge;

import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.worker.Worker;

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
public class MergeWorkerFactory {
    private ApplicationProperties applicationProperties;
    private List<MergeWorker> workers;

    public MergeWorkerFactory(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        this.workers = new ArrayList<MergeWorker>();
    }

    public void process() {
        System.out.println(applicationProperties.getApplicationName() + " - Excecução dos MergeWorkers.");

        createMergeWorkers();
        ExecutorService workersExecutor = Executors.newFixedThreadPool(applicationProperties.getNumberOfWorkers());
        for (MergeWorker woker : workers) {
            workersExecutor.execute(woker);
        }
        workersExecutor.shutdown();
        try {
            workersExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(applicationProperties.getApplicationName() + " - Erro na finalização do processamento dos arquivos. Motivo:" + e.getMessage());
        }
    }

    private void createMergeWorkers() {
        for (int i = 0; i < this.applicationProperties.getNumberOfMergeWorkers(); i++) {
            MergeWorker worker = new MergeWorker(this.applicationProperties, i);
            this.workers.add(worker);
        }
    }
}
