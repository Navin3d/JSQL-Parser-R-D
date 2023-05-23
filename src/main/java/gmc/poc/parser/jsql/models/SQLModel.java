package gmc.poc.parser.jsql.models;

import lombok.Data;

@Data
public class SQLModel {
	
	private String fileName;
	
	private String script;
	
	private ScriptType scriptType;
	
	private String path;

}
