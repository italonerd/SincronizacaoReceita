package italo.mendes.SincronizacaoReceita.com.worker;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.dto.LinhaArquivoRetaguarda;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Classe responsável por dividir o arquivo de forma balanceada e enviar para a classe Worker.
 *
 * @author Italo Mendes Rodrigues
 */

@Component
public class PrepareWorker {

    //TODO Ajustar para adicionar injeção de código
    private ApplicationProperties applicationProperties;

    public PrepareWorker(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    public void prepareRoutine(File arquivoRetaguardaCSV){
        try {
            List<LinhaArquivoRetaguarda> linhas = carregarLinhasArquivoRetaguardaCSV(arquivoRetaguardaCSV);
            WorkerFactory factory =  new WorkerFactory(applicationProperties);
            for (List<LinhaArquivoRetaguarda> linhasPerWorker : Lists.partition(linhas, applicationProperties.getMaxItemsPerWorker())) {
                factory.addWorker(new Worker(applicationProperties, linhasPerWorker));
            }
            System.out.println(applicationProperties.getApplicationName()+" - "+factory.getWorkers().size()+" Trabalhadores vão processar as "+linhas.size()+" linhas do arquivo informado.");
            factory.process();
        } catch (Exception e) {
            System.out.println(applicationProperties.getApplicationName()+" - Erro ao preparar o processamento do arquivo("+arquivoRetaguardaCSV.getName()+"). Motivo:"+e.getMessage());
        }
    }

    private static List<LinhaArquivoRetaguarda> carregarLinhasArquivoRetaguardaCSV(File arquivoRetaguardaCSV) throws IOException {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(arquivoRetaguardaCSV)).withSkipLines(1).withCSVParser(parser).build();

        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(LinhaArquivoRetaguarda.class);
        strategy.setColumnMapping(LinhaArquivoRetaguarda.getEntryColumns());

        CsvToBean csvToBean = new CsvToBeanBuilder(csvReader)
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        List<LinhaArquivoRetaguarda> linhas = csvToBean.parse();
        csvReader.close();

        return linhas;
    }
}


