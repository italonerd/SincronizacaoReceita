package italo.mendes.SincronizacaoReceita;

import italo.mendes.SincronizacaoReceita.com.bl.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.File;

@SpringBootApplication
public class SincronizacaoReceitaApplication implements CommandLineRunner {

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
			System.out.printf(ApplicationConstants.MESSAGE_APP_STARTED, applicationProperties.getApplicationName() , arquivoRetaguardaCSV.getName());
			builder.setArquivoRetaguardaCSV(arquivoRetaguardaCSV);
			builder.build();
		} else {
			System.out.printf(ApplicationConstants.MESSAGE_APP_START_ERROR, applicationProperties.getApplicationName());
		}
	}

}
