package italo.mendes.SincronizacaoReceita.com.worker;

import italo.mendes.SincronizacaoReceita.ApplicationConstants;
import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Classe responsável por controlar instancias da classe Worker.
 *
 * @author Italo Mendes Rodrigues
 */
@Getter
@Setter
@Component
public class WorkerFactory {

    @Autowired
    private ApplicationProperties applicationProperties;
    private List<Worker> workers;

    public WorkerFactory() {
        this.applicationProperties = new ApplicationProperties();
        this.workers = new ArrayList<Worker>();
    }

    public void process() {
        System.out.printf(ApplicationConstants.MESSAGE_WORKER_FACTORY_START, applicationProperties.getApplicationName());

        ExecutorService workersExecutor = Executors.newFixedThreadPool(applicationProperties.getNumberOfWorkers());
        for (Worker woker : workers) {
            workersExecutor.execute(woker);
        }
        workersExecutor.shutdown();
        try {
            workersExecutor.awaitTermination(applicationProperties.getMaxTimeToProcess(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.printf(ApplicationConstants.MESSAGE_WORKER_FACTORY_ERROR, applicationProperties.getApplicationName(), e.getMessage());
        }
    }
}
