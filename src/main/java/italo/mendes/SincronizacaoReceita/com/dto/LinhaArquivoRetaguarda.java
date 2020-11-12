package italo.mendes.SincronizacaoReceita.com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinhaArquivoRetaguarda {

    private String agencia;
    private String conta;
    private String saldo;
    private String status;
    private String resultado;

    public static String[] getEntryColumns(){
        return new String[] {"agencia", "conta", "saldo", "status"};
    }

    @Override
    public String toString() {
        return "{" + agencia + ";" + conta + ";" + saldo + ";" + status + ";" + resultado + "}";
    }
}
