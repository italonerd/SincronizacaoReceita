package italo.mendes.sincronizacao.com.builder;

import italo.mendes.sincronizacao.com.bl.Builder;
import italo.mendes.sincronizacao.com.bl.PrepareWorker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class BuilderTests {
    
    @Autowired
    private Builder builder;
    @Mock
    private PrepareWorker prepareMock;

    @BeforeEach
    void startMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test()
    void buildTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            builder.build();
        });
    }

    @Test()
    void buildWithParametersTest() {
        doNothing().when(prepareMock).prepareRoutine(any(File.class));
        builder.setPrepare(prepareMock);
        builder.build();
    }

}
