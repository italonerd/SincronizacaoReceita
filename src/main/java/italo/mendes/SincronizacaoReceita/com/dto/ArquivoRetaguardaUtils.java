package italo.mendes.SincronizacaoReceita.com.dto;

import com.opencsv.*;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class ArquivoRetaguardaUtils {

    public List<ArquivoRetaguarda> loadArquivoRetaguarda(File arquivoRetaguardaCSV, int skipLines, char separator) throws IOException {
        CSVParser parser = new CSVParserBuilder().withSeparator(separator).build();
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(arquivoRetaguardaCSV)).withSkipLines(skipLines).withCSVParser(parser).build();

        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(ArquivoRetaguarda.class);

        CsvToBean csvToBean = new CsvToBeanBuilder(csvReader)
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        List<ArquivoRetaguarda> linhas = csvToBean.parse();
        csvReader.close();

        return linhas;
    }

    public void generateCSVFile(ApplicationProperties applicationProperties, List<ArquivoRetaguarda> linesToWork, String fileName, boolean createDirectory, boolean withHeaders) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        if(createDirectory){
            String directoryName = applicationProperties.getPathTemp();
            File directory = new File(directoryName);
            if (! directory.exists()){
                directory.mkdir();
            }
            fileName = directoryName +"/" + fileName;
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

    public void cleanTempFolder(ApplicationProperties applicationProperties) throws IOException {
        String directoryName = applicationProperties.getPathTemp();
        File directory = new File(directoryName);
        FileUtils.deleteDirectory(directory);
    }

    public void readFileContent(String filePath) throws IOException {
        System.out.println(Files.readString(Path.of(filePath)));
    }
}
