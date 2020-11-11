package italo.mendes.SincronizacaoReceita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.File;

@SpringBootApplication
public class SincronizacaoReceitaApplication implements CommandLineRunner {

	@Value("${application.name}")
	private String applicationName;

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoReceitaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if(args.length > 0)
		{
			String arquivoRetaguardaCSVPath = args[0];
			File arquivoRetaguardaCSV = new File(arquivoRetaguardaCSVPath);
			System.out.println(this.applicationName+" - Iniciando o processamento do arquivo "+arquivoRetaguardaCSV.getName());
            //TODO Criar configurações de Banco de dados.
			//TODO chamar a classe Builder.
		} else {
			System.out.println(this.applicationName + " - Pelo menos um arquivo de retaguarda deve ser informado. Ex. '../sample.csv");
		}
	}

}
