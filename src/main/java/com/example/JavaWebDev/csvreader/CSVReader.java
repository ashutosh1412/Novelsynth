package com.example.JavaWebDev.csvreader;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class CSVReader {

	private Map<String, Data> surveyData;

	public CSVReader() throws IOException {

		Map<String, List<List<String>>> fileData = this.readFile();
		this.surveyData = new LinkedHashMap<String, Data>();
		for (String fileName : fileData.keySet()) {
			List<String> headers = fileData.get(fileName).get(0);
			List<String> units = fileData.get(fileName).get(1);
			List<List<Object>> list = new ArrayList<List<Object>>();
			for (int i = 0; i < headers.size(); i++) {
				list.add(new ArrayList<Object>());
			}
			for (int i = 2; i < fileData.get(fileName).size(); i++) {
				List<String> row = fileData.get(fileName).get(i);
				for (int j = 0; j < row.size(); j++) {
					if (headers.get(j).equalsIgnoreCase("Date")) {
						try {
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
							list.get(j).add(simpleDateFormat.parse(row.get(j)));
						} catch (IllegalArgumentException | ParseException e) {
							list.get(j).add(row.get(j));
						}
					} else {
						try {
							list.get(j).add(Double.parseDouble(row.get(j)));
						} catch (NumberFormatException | NullPointerException e) {
							list.get(j).add(row.get(j));
						}

					}
				}
			}
			System.out.println();
			this.surveyData.put(fileName, new Data(headers, units, list));
		}

	}

	public Map<String, List<List<String>>> readFile() throws IOException {

		ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = patternResolver.getResources("*.csv");
		Map<String, List<List<String>>> fileData = new LinkedHashMap<String, List<List<String>>>();
		for (Resource resource : resources) {
			FileReader fileReader = new FileReader(resource.getFile());
			Scanner sc = new Scanner(fileReader);
			List<List<String>> data = new ArrayList<List<String>>();
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				List<String> list = new ArrayList<String>();
				for (String value : line.split(",")) {
					list.add(value);
				}
				data.add(list);
			}
			fileData.put(resource.getFilename().replace(".csv", ""), data);
		}
		return fileData;
	}

	public Map<String, Data> getData() {
		return surveyData;
	}

}