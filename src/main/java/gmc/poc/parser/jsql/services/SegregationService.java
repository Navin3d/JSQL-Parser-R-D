package gmc.poc.parser.jsql.services;

public interface SegregationService {
	public void classifyScripts();
	public Boolean copyScript(String path, String pathToMove);
}
