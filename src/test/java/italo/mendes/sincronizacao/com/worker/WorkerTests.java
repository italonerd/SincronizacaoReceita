package italo.mendes.sincronizacao.com.worker;

import italo.mendes.sincronizacao.ApplicationConstants;
import italo.mendes.sincronizacao.ApplicationProperties;
import italo.mendes.sincronizacao.com.dto.ArquivoRetaguarda;
import italo.mendes.sincronizacao.com.dto.ArquivoRetaguardaUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class WorkerTests {

    @Autowired
    private ApplicationProperties applicationProperties;

    private ArquivoRetaguardaUtils utils;
    private File arquivoRetaguardaCSV;
    private int skipLines;
    private char separator;
    private Worker worker;
    private int id = 0;

    @BeforeEach
    void beforeEach() throws Exception {
        utils = new ArquivoRetaguardaUtils();
        arquivoRetaguardaCSV = new File(ApplicationConstants.SAMPLE_CSV_FILE);
        skipLines = 1;
        separator = ',';

        utils.cleanTempFolder(applicationProperties.getPathTemp());
        ArquivoRetaguarda arquivoRetaguarda =  new ArquivoRetaguarda();
        arquivoRetaguarda.setAgencia("0101");
        arquivoRetaguarda.setResultado("true");
        arquivoRetaguarda.setConta("12225-6");
        arquivoRetaguarda.setSaldo("100,00");
        arquivoRetaguarda.setStatus("A");

        List<ArquivoRetaguarda> tempFiles = new ArrayList<>();
        tempFiles.add(arquivoRetaguarda);

        worker = new Worker(applicationProperties, tempFiles, id);
    }

    @AfterEach
    void afterEach() throws IOException {
        utils.cleanTempFolder(applicationProperties.getPathTemp());
    }

    @Test
    void runTest(){
        worker.run();
    }
}
