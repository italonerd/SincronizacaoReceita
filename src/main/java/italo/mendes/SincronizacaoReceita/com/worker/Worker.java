package italo.mendes.SincronizacaoReceita.com.worker;

import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import italo.mendes.SincronizacaoReceita.com.dto.LinhaArquivoRetaguarda;
import italo.mendes.SincronizacaoReceita.com.service.ReceitaService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Worker(ApplicationProperties applicationProperties, List<LinhaArquivoRetaguarda> linesToWork) {
        this.applicationProperties = applicationProperties;
        this.linesToWork = linesToWork;
    }

    @Override
    public void run() {
        try {
            //TODO Recebe um processo com seus items
            //TODO Atualiza o processo(PROCESS_INICIADO)
            //TODO Chama AtualizarConta para items informados
            ReceitaService receitaService = new ReceitaService();
            for (LinhaArquivoRetaguarda line : linesToWork) {
                Boolean resultado = receitaService.atualizarConta(line.getAgencia(), line.getConta(), Double.valueOf(line.getSaldo()), line.getStatus());
                line.setResultado(resultado.toString());
            }
            //TODO Gera o arquivo temporario com os items processados
            //TODO Atualiza o processo(PROCESS_ENCERRADO)
            //TODO Chama o WorkerFinisher
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
