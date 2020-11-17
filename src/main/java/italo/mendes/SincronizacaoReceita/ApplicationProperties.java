package italo.mendes.SincronizacaoReceita;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Configuration
public class ApplicationProperties {

    @Value("${application.name}")
    private String applicationName;
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
