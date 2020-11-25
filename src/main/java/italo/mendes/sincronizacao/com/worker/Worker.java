package italo.mendes.sincronizacao.com.worker;

import italo.mendes.sincronizacao.ApplicationConstants;
import italo.mendes.sincronizacao.ApplicationProperties;
import italo.mendes.sincronizacao.com.dto.ArquivoRetaguarda;
import italo.mendes.sincronizacao.com.dto.ArquivoRetaguardaUtils;
import italo.mendes.sincronizacao.com.service.ReceitaService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Classe responsável executar o processamento do arquivo.
 *
 * @author Italo Mendes Rodrigues
 */
@Slf4j
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
                boolean resultado = receitaService.atualizarConta(
                        line.getAgencia(),
                        line.getConta().replace("-",""),
                        Double.parseDouble(line.getSaldo().replace(",",".")),
                        line.getStatus());
                line.setResultado(String.valueOf(resultado));
            }

            utils.generateCSVFile(applicationProperties.getPathTemp(), linesToWork, id + ApplicationConstants.CSV_FILE, true, false);
            log.info(String.format(ApplicationConstants.MESSAGE_WORKER_STARTED, this.id));
        } catch (Exception e) {
            log.error(String.format(ApplicationConstants.MESSAGE_WORKER_ERROR, this.id, e.getMessage()));
        }
    }
}
