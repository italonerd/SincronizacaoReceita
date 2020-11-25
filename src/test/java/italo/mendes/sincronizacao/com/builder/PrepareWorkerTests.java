package italo.mendes.sincronizacao.com.builder;

import italo.mendes.sincronizacao.com.bl.PrepareWorker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PrepareWorkerTests {

    @Autowired
    private PrepareWorker prepareWorker;

    @Test()
    void runTest() {
        Assertions.assertThrows(NullPointerException.class, () -> prepareWorker.prepareRoutine(null));
    }
}
