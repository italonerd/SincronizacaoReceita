package italo.mendes.sincronizacao;

public class ApplicationConstants {
    public static final String CSV_FILE = ".csv";
    public static final String SAMPLE_CSV_FILE = "./src/main/resources/sample.csv";

    static final String MESSAGE_APP_STARTED = "Iniciando o processamento do arquivo %s.";
    static final String MESSAGE_APP_START_ERROR = "Pelo menos um arquivo de retaguarda deve ser informado. Ex. '../sample.csv'.";

    public static final String MESSAGE_PREPARE_WORKER_ERROR = "Erro ao preparar o processamento do arquivo(%s). Motivo:%s.";

    public static final String MESSAGE_MERGE_WORKER_ERROR = "Erro na finalização do processamento dos arquivos. Motivo: %s.";
    public static final String MESSAGE_MERGE_WORKER_LOAD_TEMP_ERROR_PROBLEM = "Erro na finalização do processamento dos arquivos. Motivo: %s.";

    public static final String MESSAGE_MERGE_WORKER_FACTORY_START = "Excecução dos MergeWorkers.";
    public static final String MESSAGE_MERGE_WORKER_FACTORY_ERROR = "Erro na finalização do processamento dos arquivos. Motivo:%s.";

    public static final String MESSAGE_WORKER_STARTED = "Worker: %d executado com sucesso.";
    public static final String MESSAGE_WORKER_ERROR = "Erro no processamento do dos arquivos. Worker: %d; Motivo:%s.";

    public static final String MESSAGE_WORKER_FACTORY_START = "Excecução dos Workers.";
    public static final String MESSAGE_WORKER_FACTORY_ERROR = "Erro no processamento dos arquivos. Motivo:%s.";
    public static final String MESSAGE_WORKER_FACTORY_LOAD = "%d Trabalhadores vão processar as %d linhas do arquivo informado.";

    public static final String MESSAGE_RESULTADO = "Arquivo final gerado em \"%s\" ou logo abaixo:";

    public static final String MESSAGE_GENERATE_FILE_DIRECTORY_ERROR = "Não foi possível criar a pasta para o arquivo processado!";

    public static final String MESSAGE_LOAD_TEMP_FILES_ERROR = "Erro na finalização do processamento dos arquivos. Motivo: %s.";
}

