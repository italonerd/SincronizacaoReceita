package italo.mendes.SincronizacaoReceita.com.bl;

import italo.mendes.SincronizacaoReceita.ApplicationProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Classe responsável por construir a rotina de processamento de arquivos.
 *
 * @author Italo Mendes Rodrigues
 */
@Component
@Getter
@Setter
public class Builder {


    @Autowired
    private PrepareWorker prepare;
    private File arquivoRetaguardaCSV;

    public void build(){
        /*TODO
           O ideal seria fazer o controle de rotinas e arquivos para processar utilizando banco de dados,
           assim conseguindo um melhor controle de qual arquivo está sendo processado no momento, permitir
           uma maior divisão e multiprocessamento. Minha ideia seria constuir 3 aplicações diferentes uma
           responsável por receber arquivos(Builder), outra por processar eles(Worker) e outra de finalizar
           e entregar o processamento(Merge).
        */
        prepare.prepareRoutine(this.arquivoRetaguardaCSV);
    }
}
