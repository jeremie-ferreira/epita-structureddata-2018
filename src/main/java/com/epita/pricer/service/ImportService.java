package com.epita.pricer.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.stereotype.Service;

@Service
public class ImportService {
	
	private static final String LINE_COSTS_FILEPATH = "D:\\EPITA\\structureddata\\workspace\\epita-structureddata-2018\\resources\\data\\line-costs.csv";
	private static final String SEPARATOR = ";";

	public void importLineCosts() {
		try {
			FileReader fr = new FileReader(new File(LINE_COSTS_FILEPATH));
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			// skipping header line
			line = br.readLine();
			
			while(line != null) {
				String[] split = line.split(SEPARATOR);
				
				line = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
