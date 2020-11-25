package italo.mendes.sincronizacao.com.dto;

import com.opencsv.*;
import com.opencsv.bean.*;
import italo.mendes.sincronizacao.ApplicationConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Classe com métodos comuns relacionados a manipulação de arquivos.
 *
 * @author Italo Mendes Rodrigues
 */
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class ArquivoRetaguardaUtils {

    public List<ArquivoRetaguarda> loadArquivoRetaguarda(File arquivoRetaguardaCSV, int skipLines, char separator) throws IOException {
        CSVParser parser = new CSVParserBuilder().withSeparator(separator).build();
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(arquivoRetaguardaCSV)).withSkipLines(skipLines).withCSVParser(parser).build();

        ColumnPositionMappingStrategy<ArquivoRetaguarda> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(ArquivoRetaguarda.class);

        CsvToBeanBuilder<ArquivoRetaguarda> csvToBeanBuilder = new CsvToBeanBuilder<>(csvReader);
        csvToBeanBuilder.withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true);
        CsvToBean<ArquivoRetaguarda> csvToBean = csvToBeanBuilder
                .build();

        List<ArquivoRetaguarda> linhas = csvToBean.parse();
        csvReader.close();

        return linhas;
    }

    public String generateFolder(File directory) throws Exception {
        if (!directory.exists()) {
            if(!directory.mkdir()){
                throw new Exception(ApplicationConstants.MESSAGE_GENERATE_FILE_DIRECTORY_ERROR);
            }
        }
        return directory.getPath();
    }

    public void generateCSVFile(String directoryName, List<ArquivoRetaguarda> linesToWork, String fileName, boolean createDirectory, boolean withHeaders) throws Exception{
        if(createDirectory){
            fileName = generateFolder(new File(directoryName)) +"/" + fileName;
        }

        Writer writer = new FileWriter(fileName);
        if (withHeaders){
            writer.write(ArquivoRetaguarda.getProcessedColumnsHeaders()+ System.getProperty("line.separator")  );
        }
        StatefulBeanToCsvBuilder<ArquivoRetaguarda> builder = new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<ArquivoRetaguarda> beanWriter = builder.withSeparator(';').withQuotechar('\0').build();

        beanWriter.write(linesToWork);
        writer.close();
    }

    public void cleanTempFolder(String directoryName) throws IOException {
        File directory = new File(directoryName);
        FileUtils.deleteDirectory(directory);
    }

    public void readFileContent(String filePath) throws IOException {
        log.info("\n"+Files.readString(Path.of(filePath)));
    }
}
