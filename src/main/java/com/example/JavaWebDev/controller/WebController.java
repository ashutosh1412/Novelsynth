package com.example.JavaWebDev.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.JavaWebDev.csvreader.CSVReader;

@Controller
public class WebController {

	@Autowired
	@Lazy
	private CSVReader csvReader;

	@GetMapping("/surveydata")
	@ResponseBody
	public ResponseEntity<Object> getSurveyData() {
		ResponseEntity<Object> entity = null;
		try {
			Map<String, List<Double>> surveyData = csvReader.getSurveyData();
			entity = new ResponseEntity<Object>(surveyData, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<Object>("Unable to proceed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return entity;
	}
}
