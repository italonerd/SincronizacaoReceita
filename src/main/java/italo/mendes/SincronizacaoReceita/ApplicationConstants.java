package italo.mendes.SincronizacaoReceita;

import java.util.Locale;

public class ApplicationConstants {
    public static final String MESSAGE_APP_STARTED = "%s - Iniciando o processamento do arquivo %s \n";
    public static final String MESSAGE_APP_START_ERROR = "%s - Pelo menos um arquivo de retaguarda deve ser informado. Ex. '../sample.csv'\n";
    public static final String MESSAGE_WORKER_FACTORY_LOAD = "%s - %d Trabalhadores vão processar as %d linhas do arquivo informado.\n";
    public static final String MESSAGE_PREPARE_WORKER_ERROR = "$s - Erro ao preparar o processamento do arquivo(%s). Motivo:%s\n";
    public static final String MESSAGE_MERGE_WORKER_PROBLEM = "%s - Erro na finalização do processamento dos arquivos. Motivo: %s\n";
    public static final String MESSAGE_LOAD_TEMP_FILES_ERROR = "%s - Erro na finalização do processamento dos arquivos. Motivo: %s\n";
    public static final String MESSAGE_MERGE_WORKER_FACTORY_START = "%s - Excecução dos MergeWorkers.\n";
    public static final String MESSAGE_MERGE_WORKER_FACTORY_ERROR = "%s - Erro na finalização do processamento dos arquivos. Motivo:%s\n";
    public static final String FILE_CSV = ".csv";
    public static final String MESSAGE_WORKER_STARTED = "%s - Worker: %d executado com sucesso.\n";
    public static final String MESSAGE_WORKER_ERROR = "%s - Erro no processamento do dos arquivos. Worker: %d; Motivo:%s\n";
    public static final String MESSAGE_WORKER_FACTORY_START = "%s - Excecução dos Workers.\n";
    public static final String MESSAGE_WORKER_FACTORY_ERROR = "%s - Erro no processamento dos arquivos. Motivo:%s\n";
    public static final String MESSAGE_RESULTADO = "%s - Arquivo final gerado em \"%s\" ou logo abaixo:\n";
}

