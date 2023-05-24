package gmc.poc.parser.jsql.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import gmc.poc.parser.jsql.models.ColumnModel;
import gmc.poc.parser.jsql.models.SQLModel;
import gmc.poc.parser.jsql.models.ScriptType;
import gmc.poc.parser.jsql.models.TableModel;
import gmc.poc.parser.jsql.services.CollateService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CollateServiceImpl implements CollateService {
	
	private Pattern tablePattern = Pattern.compile("CREATE TABLE ([^\\s]+) \\(");
	private Pattern columnPattern = Pattern.compile("\\s+([^\\s]+)\\s+([^\\s]+)\\s*(DEFAULT ([^\\s]+))?,");

	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

	@Bean(name = "tableData")
	public List<TableModel> tableData(@Qualifier("rawSql") Map<ScriptType, List<SQLModel>> rawSql) {
		List<TableModel> returnValue = new ArrayList<>();
		List<SQLModel> createScripts = rawSql.get(ScriptType.CREATE);
		for (SQLModel sql : createScripts) {
			Matcher tableMatcher = tablePattern.matcher(sql.getScript());			
			String tableName = null;
			if(tableMatcher.find())
				tableName = tableMatcher.group(1);			
			if(tableName != null) {
				TableModel table = getTableModel(returnValue, tableName);				
				if(table.getColumns() != null)
					returnValue.remove(table);				
				table.setColumns(getColumnFromScript(sql.getScript()));
				returnValue.add(table);
			}
		}		
		log.error(returnValue.toString());		
		return returnValue;
	}
	
	public TableModel getTableModel(List<TableModel> tables, String tableName) {
		TableModel returnValue = tables.stream().filter(table -> (table.getName().equals(tableName))).findFirst().orElse(null);
		if(returnValue == null) {
			returnValue = new TableModel();
			returnValue.setName(tableName);
			return returnValue;
		}
		return returnValue;
	}
	
	public List<ColumnModel> getColumnFromScript(String script) {
		List<ColumnModel> returnValue = new ArrayList<>();	
		Matcher tableMatcher = tablePattern.matcher(script);		
		while (tableMatcher.find()) {
			int tableEndIndex = script.indexOf(");", tableMatcher.end());
			String tableScript = script.substring(tableMatcher.start(), tableEndIndex);
			Matcher columnMatcher = columnPattern.matcher(tableScript);			
			while (columnMatcher.find()) {
				String columnName = columnMatcher.group(1);
				String dataType = columnMatcher.group(2);
				String defaultValue = columnMatcher.group(4);
				ColumnModel columnModel = new ColumnModel();
				columnModel.setColumnName(columnName);
				columnModel.setDataType(dataType);
				columnModel.setDefaultValue(defaultValue);
				returnValue.add(columnModel);
			}			
		}		
		return returnValue;
	}
	
//	TableModel foundTable = createScripts.stream().filter(sql)

	@Override
	public String findTableName(String script) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void collateCreateStatements(@Qualifier(value = "tableData") List<TableModel> tableData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void collateAlterStatements() {
		// TODO Auto-generated method stub

	}

	@Override
	public void collateInsertStatements() {
		// TODO Auto-generated method stub

	}

	@Override
	public void collateSelectStatements() {
		// TODO Auto-generated method stub

	}

}
