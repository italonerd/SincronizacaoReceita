package italo.mendes.sincronizacaoReceita.com.worker;

import italo.mendes.sincronizacaoReceita.ApplicationConstants;
import italo.mendes.sincronizacaoReceita.ApplicationProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class WorkerFactory {

    @Autowired
    private ApplicationProperties applicationProperties;
    private List<Worker> workers;

    private WorkerFactory() {
        this.applicationProperties = new ApplicationProperties();
        this.workers = new ArrayList<>();
    }

    public void process() {
        log.info(ApplicationConstants.MESSAGE_WORKER_FACTORY_START);

        ExecutorService workersExecutor = Executors.newFixedThreadPool(applicationProperties.getNumberOfWorkers());
        for (Worker woker : workers) {
            workersExecutor.execute(woker);
        }
        workersExecutor.shutdown();
        try {
            workersExecutor.awaitTermination(applicationProperties.getMaxTimeToProcess(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error(String.format(ApplicationConstants.MESSAGE_WORKER_FACTORY_ERROR, e.getMessage()));
        }
    }
}
