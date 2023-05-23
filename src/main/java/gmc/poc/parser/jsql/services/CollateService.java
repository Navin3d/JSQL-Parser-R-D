package gmc.poc.parser.jsql.services;

import java.util.List;

import gmc.poc.parser.jsql.models.TableModel;

public interface CollateService {
	public void process();
	
	public String findTableName(String script);
	
	public void collateCreateStatements(List<TableModel> tableData);
	public void collateAlterStatements();
	public void collateInsertStatements();
	public void collateSelectStatements();
}
