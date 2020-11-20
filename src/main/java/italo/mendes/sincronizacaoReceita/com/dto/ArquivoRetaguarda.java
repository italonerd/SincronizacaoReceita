package italo.mendes.sincronizacaoReceita.com.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArquivoRetaguarda {

    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "agencia")
    private String agencia;
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "conta")
    private String conta;
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "saldo")
    private String saldo;
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "resultado")
    private String status;
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "resultado")
    private String resultado;

    static String getProcessedColumnsHeaders(){
        return "agencia;conta;saldo;status;resultado";
    }

    @Override
    public String toString() {
        return "{" + agencia + ";" + conta + ";" + saldo + ";" + status + ";" + resultado + "}";
    }
}
