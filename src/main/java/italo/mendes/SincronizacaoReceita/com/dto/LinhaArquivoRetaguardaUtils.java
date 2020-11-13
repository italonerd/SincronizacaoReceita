package italo.mendes.SincronizacaoReceita.com.dto;

import com.opencsv.*;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.dto.LinhaArquivoRetaguarda;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

/**
 * Classe com métodos comuns do projeto
 *
 * @author Italo Mendes Rodrigues
 */
@Getter
@Setter
@NoArgsConstructor
@Component
public class LinhaArquivoRetaguardaUtils {

    public List<LinhaArquivoRetaguarda> carregarLinhasArquivoRetaguardaCSV(File arquivoRetaguardaCSV, int skipLines, char separator) throws IOException {
        CSVParser parser = new CSVParserBuilder().withSeparator(separator).build();
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(arquivoRetaguardaCSV)).withSkipLines(skipLines).withCSVParser(parser).build();

        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(LinhaArquivoRetaguarda.class);

        CsvToBean csvToBean = new CsvToBeanBuilder(csvReader)
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        List<LinhaArquivoRetaguarda> linhas = csvToBean.parse();
        csvReader.close();

        return linhas;
    }

    public void generateCSVFile(ApplicationProperties applicationProperties, List<LinhaArquivoRetaguarda> linesToWork, String fileName, boolean createDirectory, boolean withHeaders) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
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
            writer.write(LinhaArquivoRetaguarda.getProcessedColumns()+ System.getProperty("line.separator")  );
        }
        StatefulBeanToCsvBuilder<LinhaArquivoRetaguarda> builder = new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<LinhaArquivoRetaguarda> beanWriter = builder.withSeparator(';').build();

        beanWriter.write(linesToWork);
        writer.close();
    }

    public void cleanTempFolder(ApplicationProperties applicationProperties) throws IOException {
        String directoryName = applicationProperties.getPathTemp();
        File directory = new File(directoryName);
        FileUtils.deleteDirectory(directory);
    }
}
