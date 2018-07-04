package com.epita.pricer.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ImportService {
	
	private static final String LINE_COSTS_FILEPATH = "line-costs.csv";
	private static final String CSV_SEPARATOR = ";";

	
	@EventListener(ApplicationReadyEvent.class)
	public void importLineCosts() {
		
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(new ClassPathResource(LINE_COSTS_FILEPATH).getFile());
			br = new BufferedReader(fr);
			
			String line = br.readLine();
			// Skipping header line
			line = br.readLine();
			
			while(line != null) {
				String[] split = line.split(CSV_SEPARATOR);
				
				//TODO: store the file data into MarshallingYards and Lines Objects
				
				line = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
