package gmc.poc.parser.jsql.models;

import lombok.Data;

@Data
public class ColumnModel {
	private String columnName;
	private String dataType;
	private String defaultValue;
}
