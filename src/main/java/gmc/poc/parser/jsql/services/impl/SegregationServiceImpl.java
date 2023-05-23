package gmc.poc.parser.jsql.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import gmc.poc.parser.jsql.models.SQLModel;
import gmc.poc.parser.jsql.models.ScriptType;
import gmc.poc.parser.jsql.services.SegregationService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;

@Slf4j
@Service
public class SegregationServiceImpl implements SegregationService {

	@Value("${config.directory.path}")
	private String baseDir;

	@Override
	public void process() {
		
	}

	@Bean(name = "rawSql")
	public Map<ScriptType, List<SQLModel>> rawSql() throws IOException {
		Map<ScriptType, List<SQLModel>> returnValue = new HashMap<>();
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resourcePatternResolver.getResources("/" + baseDir + "/input/*.sql");
		for (Resource resource : resources) {
			SQLModel sqlModel = new SQLModel();
			sqlModel.setFileName(resource.getFilename());
			byte[] bData = FileCopyUtils.copyToByteArray(resource.getInputStream());
			String tempScript = (new String(bData, StandardCharsets.UTF_8)).trim();
			sqlModel.setScript(tempScript);
			ScriptType scriptType = classifyScripts(tempScript);
			if (returnValue.get(scriptType) != null)
				returnValue.get(scriptType).add(sqlModel);
			else {
				List<SQLModel> listModel = new ArrayList<>();
				listModel.add(sqlModel);
				returnValue.put(scriptType, listModel);
			}
		}
		return returnValue;
	}

	@Override
	public ScriptType classifyScripts(String sqlScript) {
		try {
			Statement statement = CCJSqlParserUtil.parse(sqlScript);
			if (statement instanceof CreateTable)
				return ScriptType.CREATE;
			else if (statement instanceof Insert)
				return ScriptType.INSERT;
			else if (statement instanceof Alter)
				return ScriptType.ALTER;
			else if (statement instanceof Select)
				return ScriptType.SELECT;
			else
				return ScriptType.UNKNOWN;
		} catch (JSQLParserException e) {
			log.error("Error parsing SQL script: " + e.getMessage());
		}
		return ScriptType.UNKNOWN;
	}

	@Override
	public Boolean copyScript(String sourcePathString, String destinationPathString) {
		File sourceFile = new File(sourcePathString); // Replace with the actual source file path
		File destinationFolder = new File(destinationPathString); // Replace with the actual destination folder path
		try {
			// Create the destination folder if it doesn't exist
			if (!destinationFolder.exists())
				destinationFolder.mkdirs();
			// Copy the file to the destination folder
			Path destinationPath = destinationFolder.toPath().resolve(sourceFile.getName());
			Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
			log.info("The file at Path: {}, has been copied to Path: {}", sourcePathString, destinationPathString);
		} catch (IOException e) {
			log.error("Error copying files...");
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
