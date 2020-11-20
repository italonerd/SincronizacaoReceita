package italo.mendes.sincronizacaoReceita.dto;

import italo.mendes.sincronizacaoReceita.ApplicationConstants;
import italo.mendes.sincronizacaoReceita.ApplicationProperties;
import italo.mendes.sincronizacaoReceita.com.dto.ArquivoRetaguarda;
import italo.mendes.sincronizacaoReceita.com.dto.ArquivoRetaguardaUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ArquivoRetaguardaUtilsTests {

    @Autowired
    private ApplicationProperties applicationProperties;

    private static final String INVALID_FILE = "INVALID_FILE";
    private ArquivoRetaguardaUtils utils;
    private File arquivoRetaguardaCSV;
    private int skipLines;
    private char separator;

    @BeforeEach
    void beforeEach(){
        utils = new ArquivoRetaguardaUtils();
        arquivoRetaguardaCSV = new File(ApplicationConstants.SAMPLE_CSV_FILE);
        skipLines = 1;
        separator = ',';
    }

    @Test
    void loadArquivoRetaguardaTest(){
        Assertions.assertDoesNotThrow(() -> {
            utils.loadArquivoRetaguarda(arquivoRetaguardaCSV, skipLines, separator);
        });
    }

    @Test
    void loadArquivoRetaguardaNullPointerExceptionTest(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            utils.loadArquivoRetaguarda(null, 0, ',');
        });
    }

    @Test
    void loadArquivoRetaguardaIOExceptionTest(){
        Assertions.assertThrows(IOException.class, () -> {
            utils.loadArquivoRetaguarda(new File(INVALID_FILE), skipLines, separator);
        });
    }

    @Test
    void generateCSVFileTest(){
        Assertions.assertDoesNotThrow(() -> {
            ArquivoRetaguarda arquivoRetaguarda =  new ArquivoRetaguarda();
            arquivoRetaguarda.setAgencia("0101");
            arquivoRetaguarda.setResultado("true");
            arquivoRetaguarda.setConta("12225-6");
            arquivoRetaguarda.setSaldo("100,00");
            arquivoRetaguarda.setStatus("A");

            List<ArquivoRetaguarda> tempFiles = new ArrayList<>();
            tempFiles.add(arquivoRetaguarda);
            utils.generateCSVFile(applicationProperties.getPathTemp(),tempFiles,INVALID_FILE + ApplicationConstants.CSV_FILE,true,false);
            utils.cleanTempFolder(applicationProperties.getPathTemp());
        });
    }

    @Test
    void generateFolderTest(){
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertEquals(applicationProperties.getPathTemp(), utils.generateFolder(new File(applicationProperties.getPathTemp())));
        });
    }

    @Test
    void generateFolderSecurityExceptionTest(){
        File folder = Mockito.mock(File.class);
        when(folder.exists()).thenReturn(false);
        when(folder.mkdir()).thenThrow(SecurityException.class);

        Assertions.assertThrows(SecurityException.class, () -> {
            Assertions.assertEquals(applicationProperties.getPathTemp(), utils.generateFolder(folder));
        });
    }

    @Test
    void generateFolderExceptionTest(){
        File folder = Mockito.mock(File.class);
        when(folder.exists()).thenReturn(false);
        when(folder.mkdir()).thenReturn(false);

        Assertions.assertThrows(Exception.class, () -> {
            Assertions.assertEquals(applicationProperties.getPathTemp(), utils.generateFolder(folder));
        });
    }

    @Test
    void cleanTempFolderTest(){
        Assertions.assertDoesNotThrow(() -> {
            utils.cleanTempFolder(applicationProperties.getPathTemp());
        });
    }

    @Test
    void readFileContent(){
        Assertions.assertDoesNotThrow(() -> {
            ArquivoRetaguarda arquivoRetaguarda =  new ArquivoRetaguarda();
            arquivoRetaguarda.setAgencia("12225-6");
            arquivoRetaguarda.setResultado("true");
            arquivoRetaguarda.setConta("0101");
            arquivoRetaguarda.setSaldo("100,00");
            arquivoRetaguarda.setStatus("A");

            List<ArquivoRetaguarda> tempFiles = new ArrayList<>();
            tempFiles.add(arquivoRetaguarda);
            utils.generateCSVFile(applicationProperties.getPathTemp(),tempFiles,INVALID_FILE + ApplicationConstants.CSV_FILE,true,false);
            utils.readFileContent(applicationProperties.getPathTemp() +"/" + INVALID_FILE + ApplicationConstants.CSV_FILE);
            utils.cleanTempFolder(applicationProperties.getPathTemp());
        });
    }
}
