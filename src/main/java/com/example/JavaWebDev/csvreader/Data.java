package com.example.JavaWebDev.csvreader;

import java.util.List;

public class Data {
	private List<String> headers;
	private List<String> units;
	private List<List<Object>> data;

	public Data(List<String> headers, List<String> units, List<List<Object>> data) {
		super();
		this.headers = headers;
		this.units = units;
		this.data = data;
	}

	public Data() {
		
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<String> getUnits() {
		return units;
	}

	public void setUnits(List<String> units) {
		this.units = units;
	}

	public List<List<Object>> getData() {
		return data;
	}

	public void setData(List<List<Object>> data) {
		this.data = data;
	}
}
