package italo.mendes.SincronizacaoReceita.com.worker;

import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public void addWorker(Worker worker){
        this.workers.add(worker);
    }

    public void process() {
        //TODO Executar os workers
        System.out.println(applicationProperties.getApplicationName()+" - Excecução dos Workers.");
    }

}
