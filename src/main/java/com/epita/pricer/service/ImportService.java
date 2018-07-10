package com.epita.pricer.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.epita.pricer.domain.JsonStationDeserializer;
import com.epita.pricer.entity.Line;
import com.epita.pricer.entity.MarshallingYard;
import com.epita.pricer.entity.Station;
import com.epita.pricer.repository.LineRepository;
import com.epita.pricer.repository.MarshallingYardRepository;
import com.epita.pricer.repository.StationRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

@Service
public class ImportService {
	
	private static final String STATIONS_FILEPATH = "data/gares-fret5.xml";
	private static final String STATIONS_JSON_FILEPATH = "data/liste-des-gares.json";
	private static final String LINE_COSTS_FILEPATH = "data/line-costs.csv";
	private static final String CSV_SEPARATOR = ";";

	@Autowired
	StationRepository stationRepository;
	@Autowired
	MarshallingYardRepository marshallingYardRepository;
	@Autowired
	LineRepository lineRepository;
	
	@EventListener(ApplicationReadyEvent.class)
	public void importData() throws Exception {
		Map<Integer, Station> stationsMap = importStations();
		Map<String, MarshallingYard> yardsMap = importMarshallingYards(stationsMap);
		importLineCosts(yardsMap);
	}
	
	private Map<Integer, Station> importStations() throws Exception {
		Map<Integer, Station> stationsMap = new HashMap<>();
		
		// Import from XML stations file
		File f = new ClassPathResource(STATIONS_FILEPATH).getFile();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(f);
		
		NodeList list = document.getElementsByTagName("gare");
		
		for (int i = 0; i < list.getLength(); ++i) {
			Station station = new Station();
			Element stationElt = (Element) list.item(i);
			
			station.setCode(Integer.parseInt(stationElt.getAttribute("code")));
			station.setName(stationElt.getElementsByTagName("libelle").item(0).getTextContent());
			station.setMarshallingYard(null);
			
			stationsMap.put(station.getCode(), station);
		}

		// Import from Json
		Gson gson = new GsonBuilder().registerTypeAdapter(Station[].class, new JsonStationDeserializer()).create();

		JsonReader reader = new JsonReader(new InputStreamReader(new ClassPathResource(STATIONS_JSON_FILEPATH).getInputStream()));
		Station[] jsonStations = (Station[]) gson.fromJson(reader, Station[].class);
		
		for (Station jsonStation : jsonStations) {
			if (stationsMap.containsKey(jsonStation.getCode())) {
				Station station = stationsMap.get(jsonStation.getCode());
				station.setLatitude(jsonStation.getLatitude());
				station.setLongitude(jsonStation.getLongitude());
				station.setName(jsonStation.getName());
			}
		}
		
		// persist stations
		for (Station station : stationsMap.values()) {
			station = stationRepository.save(station);
		}
		
		return stationsMap;
	}
	
	private Map<String, MarshallingYard> importMarshallingYards(Map<Integer, Station> stationsMap) throws Exception {
		Map<String, MarshallingYard> yardsMap = new HashMap<>();
		
		// Import from XML stations file
		File f = new ClassPathResource(STATIONS_FILEPATH).getFile();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(f);
		
		NodeList plateformes = document.getElementsByTagName("plateforme");
		
		for (int i = 0; i < plateformes.getLength(); ++i) {			
			Element yardElt = (Element) plateformes.item(i);
			String yardCode = yardElt.getAttribute("code");
			Integer stationCode = Integer.parseInt(yardElt.getElementsByTagName("code-gare").item(0).getTextContent());
			
			if (!yardsMap.containsKey(yardCode)) {
				MarshallingYard yard = new MarshallingYard();
				yard.setCode(yardCode);
				yard.setStation(stationsMap.get(stationCode));
				yardsMap.put(yardCode, yard);
			}
		}
		
		//persist yards
		for (MarshallingYard yard : yardsMap.values()) {
			yard = marshallingYardRepository.save(yard);
		}
		
		NodeList gares = document.getElementsByTagName("gare");
		// link stations to Yards
		for (int i = 0; i < gares.getLength(); ++i) {		
			Element stationElt = (Element) gares.item(i);
			Element yardElt = (Element) stationElt.getElementsByTagName("plateforme").item(0);
			
			Integer stationCode = Integer.parseInt(stationElt.getAttribute("code"));
			String yardCode = yardElt.getAttribute("code");
			MarshallingYard yard = yardsMap.get(yardCode);
			
			Station station = stationsMap.get(stationCode);
			station.setMarshallingYard(yard);
			
			station = stationRepository.save(station);
		}
		
		return yardsMap;
	}
	
	private void importLineCosts(Map<String, MarshallingYard> yardsMap) throws Exception {

		FileReader fr = new FileReader(new ClassPathResource(LINE_COSTS_FILEPATH).getFile());
		BufferedReader br = new BufferedReader(fr);
		
		String l = br.readLine();
		// Skipping header line
		l = br.readLine();
		
		while(l != null) {
			String[] split = l.split(CSV_SEPARATOR);
			
			try {
				Line line = new Line();
				
				MarshallingYard origin = yardsMap.get(split[0]);
				MarshallingYard destination = yardsMap.get(split[1]);
				
				line.setOrigin(origin);
				line.setDestination(destination);
				line.setCost(Float.parseFloat(split[2]));
				
				line = lineRepository.save(line);
			} catch (Exception e) {
				e.printStackTrace();
			}

			l = br.readLine();
		}
	}
}
