package gmc.poc.parser.jsql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import gmc.poc.parser.jsql.services.CollateService;
import gmc.poc.parser.jsql.services.SegregationService;

@Component
public class AppRunnerJSQL implements CommandLineRunner {
	
	@Autowired
	private SegregationService segregationService;
	@Autowired
	private CollateService collateService;

	@Override
	public void run(String... args) throws Exception {
		segregationService.process();
		collateService.process();
	}

}
