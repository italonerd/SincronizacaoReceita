package italo.mendes.sincronizacao.com.merge;

import italo.mendes.sincronizacao.ApplicationConstants;
import italo.mendes.sincronizacao.ApplicationProperties;
import italo.mendes.sincronizacao.com.dto.ArquivoRetaguarda;
import italo.mendes.sincronizacao.com.dto.ArquivoRetaguardaUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe responsável unir os arquivos processados
 *
 * @author Italo Mendes Rodrigues
 */
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class MergeWorker implements Runnable {

    private ApplicationProperties applicationProperties;
    private List<ArquivoRetaguarda> tempFiles;
    private ArquivoRetaguardaUtils arquivoUtils;

    private int id;

    public MergeWorker(ApplicationProperties applicationProperties, int id) {
        this.id = id;
        this.arquivoUtils = new ArquivoRetaguardaUtils();
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void run() {
        try {
            loadTempFiles();
            arquivoUtils.generateCSVFile(applicationProperties.getPathTemp(), tempFiles, applicationProperties.getResultFile(), false,true);
            log.info(String.format(ApplicationConstants.MESSAGE_RESULTADO, new File(applicationProperties.getResultFile()).getCanonicalPath()));
            arquivoUtils.readFileContent(applicationProperties.getResultFile());
            arquivoUtils.cleanTempFolder(applicationProperties.getPathTemp());
        }catch (Exception e){
            log.error(String.format(ApplicationConstants.MESSAGE_MERGE_WORKER_ERROR, e.getMessage()));
        }
    }

    private void loadTempFiles(){
        String directoryName = applicationProperties.getPathTemp();
        try (Stream<Path> paths = Files.walk(Paths.get(directoryName))) {
            List<List<ArquivoRetaguarda>> list = paths.filter(Files::isRegularFile)
                    .map(
                            p -> {
                                try {
                                    return arquivoUtils.loadArquivoRetaguarda(p.toFile(), 0, ';');
                                } catch (IOException e) {
                                    log.error(String.format(ApplicationConstants.MESSAGE_MERGE_WORKER_LOAD_TEMP_ERROR_PROBLEM, e.getMessage()));
                                }
                                return null;
                            }
                    )
                    .collect(Collectors.toList());

            tempFiles = list.stream().flatMap(List::stream).collect(Collectors.toList());
        } catch (IOException e) {
            log.error(String.format(ApplicationConstants.MESSAGE_LOAD_TEMP_FILES_ERROR, e.getMessage()));
        }
    }
}
