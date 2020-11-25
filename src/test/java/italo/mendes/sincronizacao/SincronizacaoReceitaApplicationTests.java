package italo.mendes.sincronizacao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SincronizacaoReceitaApplicationTests {
	@Autowired
	private SincronizacaoReceitaApplication app;

	@Test
	void runNoFileTest() {
		app.run();
	}

	@Test
	void runWithFileTest() {
		String[] args = {ApplicationConstants.SAMPLE_CSV_FILE};
		app.run(args);
	}
}
