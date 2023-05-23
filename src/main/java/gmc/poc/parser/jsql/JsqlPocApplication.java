package gmc.poc.parser.jsql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gmc.poc.parser.jsql.services.SegregationService;

@SpringBootApplication
public class JsqlPocApplication implements CommandLineRunner {
	
	@Autowired
	private SegregationService segregationService;

	public static void main(String[] args) {
		SpringApplication.run(JsqlPocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		segregationService.classifyScripts();
	}

}
