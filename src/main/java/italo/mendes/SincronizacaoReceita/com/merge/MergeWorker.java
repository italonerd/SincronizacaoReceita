package italo.mendes.SincronizacaoReceita.com.merge;

import italo.mendes.SincronizacaoReceita.ApplicationConstants;
import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.dto.ArquivoRetaguarda;
import italo.mendes.SincronizacaoReceita.com.dto.ArquivoRetaguardaUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
            arquivoUtils.generateCSVFile(applicationProperties, tempFiles, applicationProperties.getResultFile(), false,true);
            File arquivoFinal = new File(applicationProperties.getResultFile());
            System.out.printf(ApplicationConstants.MESSAGE_RESULTADO, applicationProperties.getApplicationName(), arquivoFinal.getCanonicalPath());
            arquivoUtils.readFileContent(applicationProperties.getResultFile());
            arquivoUtils.cleanTempFolder(applicationProperties);
        }catch (Exception e){
            System.out.printf(ApplicationConstants.MESSAGE_MERGE_WORKER_PROBLEM, applicationProperties.getApplicationName(), e.getMessage());
        }
    }

    public void loadTempFiles(){
        String directoryName = applicationProperties.getPathTemp();
        try (Stream<Path> paths = Files.walk(Paths.get(directoryName))) {
            List<List<ArquivoRetaguarda>> list = paths.filter(Files::isRegularFile)
                    .map(
                            p -> {
                                try {
                                    return arquivoUtils.loadArquivoRetaguarda(p.toFile(), 0, ';');
                                } catch (IOException e) {
                                    System.out.println(applicationProperties.getApplicationName() + " - Erro na finalização do processamento dos arquivos. Motivo:" + e.getMessage());
                                }
                                return null;
                            }
                    )
                    .collect(Collectors.toList());

            tempFiles = list.stream().flatMap(List::stream).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.printf(ApplicationConstants.MESSAGE_LOAD_TEMP_FILES_ERROR, applicationProperties.getApplicationName(), e.getMessage());
        }

    }
}
