package gmc.poc.parser.jsql.services;

import gmc.poc.parser.jsql.models.ScriptType;

public interface SegregationService {
	public void process();
	
	public ScriptType classifyScripts(String sqlScript);
	public Boolean copyScript(String path, String pathToMove);
}
