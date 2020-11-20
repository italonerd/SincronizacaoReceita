package italo.mendes.sincronizacaoReceita;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class ApplicationProperties {
    @Value("${application.number_of_workers}")
    private int numberOfWorkers;
    @Value("${application.number_of_merge_workers}")
    private int numberOfMergeWorkers;
    @Value("${application.max_items_per_worker}")
    private int maxItemsPerWorker;
    @Value("${application.max_time_process}")
    private int maxTimeToProcess;
    @Value("${application.pathTemp}")
    private String pathTemp;
    @Value("${application.resultFile}")
    private String resultFile;
}
