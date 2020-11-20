package italo.mendes.sincronizacaoReceita;

import italo.mendes.sincronizacaoReceita.com.bl.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@Slf4j
@SpringBootApplication
class SincronizacaoReceitaApplication implements CommandLineRunner {

	@Autowired
	private Builder builder;
	@Autowired
	private ApplicationProperties applicationProperties;

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoReceitaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		File arquivoRetaguardaCSV = null;
		if(args.length > 0) {
			arquivoRetaguardaCSV = new File(args[0]);
		}

		if(arquivoRetaguardaCSV != null && arquivoRetaguardaCSV.exists() && !arquivoRetaguardaCSV.isDirectory()){
			log.info(String.format(ApplicationConstants.MESSAGE_APP_STARTED, arquivoRetaguardaCSV.getName()));
			builder.setArquivoRetaguardaCSV(arquivoRetaguardaCSV);
			builder.build();
		} else {
			log.error(ApplicationConstants.MESSAGE_APP_START_ERROR);
		}
	}

}
