package italo.mendes.SincronizacaoReceita;

import italo.mendes.SincronizacaoReceita.com.bl.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class SincronizacaoReceitaApplication implements CommandLineRunner {

	@Autowired
	ApplicationProperties applicationProperties;

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoReceitaApplication.class, args);
	}

	@Override
	public void run(String... args) {

		File arquivoRetaguardaCSV = null;
		if(args.length > 0) {
			String arquivoRetaguardaCSVPath = args[0];
			arquivoRetaguardaCSV = new File(arquivoRetaguardaCSVPath);
		}

		if(arquivoRetaguardaCSV != null && arquivoRetaguardaCSV.exists() && !arquivoRetaguardaCSV.isDirectory()){
			System.out.println(applicationProperties.getApplicationName()+" - Iniciando o processamento do arquivo "+arquivoRetaguardaCSV.getName());
			Builder builder = new Builder(applicationProperties, arquivoRetaguardaCSV);
			builder.build();
		} else {
			System.out.println(applicationProperties.getApplicationName() + " - Pelo menos um arquivo de retaguarda deve ser informado. Ex. '../sample.csv");
		}
	}

}
