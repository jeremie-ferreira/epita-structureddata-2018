package com.epita.pricer.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.epita.pricer.entity.MarshallingYard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.epita.pricer.entity.Line;

@Service
public class ImportService {
	
	private static final String STATIONS_FILEPATH = "data/gares-fret5.xml";
	private static final String LINE_COSTS_FILEPATH = "data/line-costs.csv";
	private static final String CSV_SEPARATOR = ";";

	
	
	@EventListener(ApplicationReadyEvent.class)
	public void importData() {
		importStationsFromJson();
		
	}
	
	private void importStationsFromJson() {
		Gson gson = new GsonBuilder().create();
		
		
	}
	
	private void importStations() {
		
		File fXmlFile;
		try {
			fXmlFile = new ClassPathResource(STATIONS_FILEPATH).getFile();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(fXmlFile);
			
			Node root = document.getDocumentElement();
			
			Node node = root.getFirstChild();
			
			while (node != null) {
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					System.out.println(node.getNodeName());
					System.out.println(node.getAttributes().getNamedItem("code").getNodeValue());
				}
				
				node = node.getNextSibling();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	private void importLineCosts() {

		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(new ClassPathResource(LINE_COSTS_FILEPATH).getFile());
			br = new BufferedReader(fr);
			
			String l = br.readLine();
			// Skipping header line
			l = br.readLine();
			
			while(l != null) {
				String[] split = l.split(CSV_SEPARATOR);
				
				//TODO: store the data into MarshallingYards and Lines Objects
				MarshallingYard origin = new MarshallingYard();
				MarshallingYard destination = new MarshallingYard();
				Line line = new Line();
				
				origin.setCode(split[0]);
				destination.setCode(split[1]);
				line.setOrigin(origin);
				line.setDestination(destination);
				line.setCost(Float.parseFloat(split[3]));

				l = br.readLine();
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
