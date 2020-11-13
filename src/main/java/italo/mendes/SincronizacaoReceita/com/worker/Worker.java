package italo.mendes.SincronizacaoReceita.com.worker;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.dto.LinhaArquivoRetaguarda;
import italo.mendes.SincronizacaoReceita.com.dto.LinhaArquivoRetaguardaUtils;
import italo.mendes.SincronizacaoReceita.com.service.ReceitaService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável executar o processamento do arquivo.
 *
 * @author Italo Mendes Rodrigues
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public class Worker implements Runnable {

    //TODO Ajustar para adicionar injeção de código
    private ApplicationProperties applicationProperties;
    private List<LinhaArquivoRetaguarda> linesToWork;
    private int id;
    private LinhaArquivoRetaguardaUtils utils;

    public Worker(ApplicationProperties applicationProperties, List<LinhaArquivoRetaguarda> linesToWork, int id) {
        this.applicationProperties = applicationProperties;
        this.linesToWork = linesToWork;
        this.id = id;
        this.utils = new LinhaArquivoRetaguardaUtils();
    }

    @Override
    public void run() {
        try {
            ReceitaService receitaService = new ReceitaService();
            for (final LinhaArquivoRetaguarda line : linesToWork) {
                Boolean resultado = receitaService.atualizarConta(
                        line.getAgencia(),
                        line.getConta().replace("-",""),
                        Double.valueOf(line.getSaldo().replace(",",".")),
                        line.getStatus());
                line.setResultado(resultado.toString());
            }

            utils.generateCSVFile(applicationProperties, linesToWork,id+".csv",true, false);
            System.out.println(applicationProperties.getApplicationName()+" - Worker: "+this.id+" executado com sucesso.");
        } catch (Exception e) {
            System.out.println(applicationProperties.getApplicationName()+" - Erro no processamento do dos arquivos. Worker: "+this.id+"; Motivo:"+e.getMessage());
        }
    }
}
