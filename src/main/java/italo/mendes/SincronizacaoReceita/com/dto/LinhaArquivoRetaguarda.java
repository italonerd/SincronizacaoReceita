package italo.mendes.SincronizacaoReceita.com.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinhaArquivoRetaguarda {

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

    public static String[] getEntryColumns(){
        return new String[] {"agencia", "conta", "saldo", "status"};
    }
    public static String getProcessedColumns(){
        return "agencia;conta;saldo;status;resultado";
    }
    @Override
    public String toString() {
        return "{" + agencia + ";" + conta + ";" + saldo + ";" + status + ";" + resultado + "}";
    }
}
