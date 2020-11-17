package italo.mendes.SincronizacaoReceita.com.worker;

import italo.mendes.SincronizacaoReceita.ApplicationConstants;
import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.dto.ArquivoRetaguarda;
import italo.mendes.SincronizacaoReceita.com.dto.ArquivoRetaguardaUtils;
import italo.mendes.SincronizacaoReceita.com.service.ReceitaService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Classe responsável executar o processamento do arquivo.
 *
 * @author Italo Mendes Rodrigues
 */
@Getter
@Setter
public class Worker implements Runnable {

    private ReceitaService receitaService;
    private ApplicationProperties applicationProperties;
    private List<ArquivoRetaguarda> linesToWork;
    private int id;
    private ArquivoRetaguardaUtils utils;

    public Worker(ApplicationProperties applicationProperties, List<ArquivoRetaguarda> linesToWork, int id) {
        this.applicationProperties = applicationProperties;
        this.linesToWork = linesToWork;
        this.id = id;
        this.utils = new ArquivoRetaguardaUtils();
        this.receitaService =  new ReceitaService();
    }

    @Override
    public void run() {
        try {
            for (final ArquivoRetaguarda line : linesToWork) {
                Boolean resultado = receitaService.atualizarConta(
                        line.getAgencia(),
                        line.getConta().replace("-",""),
                        Double.valueOf(line.getSaldo().replace(",",".")),
                        line.getStatus());
                line.setResultado(resultado.toString());
            }

            utils.generateCSVFile(applicationProperties, linesToWork, id + ApplicationConstants.FILE_CSV, true, false);
            System.out.printf(ApplicationConstants.MESSAGE_WORKER_STARTED, applicationProperties.getApplicationName(), this.id);
        } catch (Exception e) {
            System.out.printf(ApplicationConstants.MESSAGE_WORKER_ERROR, applicationProperties.getApplicationName(), this.id, e.getMessage());
        }
    }
}
