package italo.mendes.SincronizacaoReceita.com.worker;

import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import lombok.Getter;
import lombok.Setter;
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
@Component
@Getter
@Setter
public class WorkerFactory {

    //TODO Ajustar para adicionar injeção de código
    private ApplicationProperties applicationProperties;
    private List<Worker> workers;

    public WorkerFactory(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        this.workers = new ArrayList<Worker>();
    }

    public void process() {
        System.out.println(applicationProperties.getApplicationName() + " - Excecução dos Workers.");

        ExecutorService workersExecutor = Executors.newFixedThreadPool(applicationProperties.getNumberOfWorkers());
        for (Worker woker : workers) {
            workersExecutor.execute(woker);
        }
        workersExecutor.shutdown();
        try {
            workersExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(applicationProperties.getApplicationName() + " - Erro no processamento dos arquivos. Motivo:" + e.getMessage());
        }
    }
}
