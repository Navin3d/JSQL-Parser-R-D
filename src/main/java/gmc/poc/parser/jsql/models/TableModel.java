package gmc.poc.parser.jsql.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TableModel {
	private String name;
	private List<ColumnModel> columns = new ArrayList<>();
}
