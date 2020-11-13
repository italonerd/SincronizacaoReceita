package italo.mendes.SincronizacaoReceita.com.merge;

import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.dto.LinhaArquivoRetaguarda;
import italo.mendes.SincronizacaoReceita.com.dto.LinhaArquivoRetaguardaUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe responsável unir os arquivos processados
 *
 * @author Italo Mendes Rodrigues
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public class MergeWorker implements Runnable {

    //TODO Ajustar para adicionar injeção de código
    private ApplicationProperties applicationProperties;
    private List<LinhaArquivoRetaguarda> tempFiles;
    private LinhaArquivoRetaguardaUtils arquivoUtils;

    private int id;

    public MergeWorker(ApplicationProperties applicationProperties, int id) {
        this.applicationProperties = applicationProperties;
        this.id = id;
        this.arquivoUtils = new LinhaArquivoRetaguardaUtils();
    }

    @Override
    public void run() {
        try {
            loadTempFiles();
            arquivoUtils.generateCSVFile(applicationProperties, tempFiles, "./ArquivoRetaguardaProcessado.csv", false,true);
            arquivoUtils.cleanTempFolder(applicationProperties);
        }catch (Exception e){
            System.out.println(applicationProperties.getApplicationName() + " - Erro na finalização do processamento dos arquivos. Motivo:" + e.getMessage());
        }
    }

    public void loadTempFiles(){
        String directoryName = applicationProperties.getPathTemp();
        try (Stream<Path> paths = Files.walk(Paths.get(directoryName))) {
            List<List<LinhaArquivoRetaguarda>> list = paths.filter(Files::isRegularFile)
                    .map(
                            p -> {
                                try {
                                    return arquivoUtils.carregarLinhasArquivoRetaguardaCSV(p.toFile(), 0, ';');
                                } catch (IOException e) {
                                    System.out.println(applicationProperties.getApplicationName() + " - Erro na finalização do processamento dos arquivos. Motivo:" + e.getMessage());
                                }
                                return null;
                            }
                    )
                    .collect(Collectors.toList());

            tempFiles = list.stream().flatMap(List::stream).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(applicationProperties.getApplicationName() + " - Erro na finalização do processamento dos arquivos. Motivo:" + e.getMessage());
        }

    }
}
