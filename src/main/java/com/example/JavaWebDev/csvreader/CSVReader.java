package com.example.JavaWebDev.csvreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class CSVReader {

	private Environment environment;

	private Map<String, List<Double>> surveyData;

	public CSVReader() throws IOException {
		Scanner sc = this.readFile();
		this.surveyData = new LinkedHashMap<String, List<Double>>();
		String headers[] = sc.nextLine().trim().split(",");
		String units[] = sc.nextLine().trim().split(",");
		int count = 0;
		for (String header : headers) {
			this.surveyData.put(header + "*" + units[count++], new ArrayList<Double>());
		}
		while (sc.hasNextLine()) {
			String line = sc.nextLine().trim();
			count = 0;
			for (String value : line.split(",")) {
				this.surveyData.get(headers[count] + "*" + units[count++]).add(Double.parseDouble(value));
			}
		}
	}

	private Scanner readFile() throws IOException {
		File pathFile = ResourceUtils.getFile("classpath:application.properties");
		Properties properties = new Properties();
		FileReader fileReader = new FileReader(pathFile);
		properties.load(fileReader);
		System.out.println(properties.getProperty("path.SurveyData"));
		FileInputStream file = new FileInputStream(properties.getProperty("path.SurveyData"));
		return new Scanner(file);
	}

	public Map<String, List<Double>> getSurveyData() {
		return surveyData;
	}

}
