package com.epita.pricer.domain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.epita.pricer.entity.Station;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonStationDeserializer implements JsonDeserializer<Station[]> {
	@Override
	public Station[] deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) {
		JsonArray array = json.getAsJsonArray();
		
		List<Station> result = new ArrayList<>();
		
		for(int i = 0; i < array.size(); ++i) {
			Station station = new Station();
			
			try {
				JsonObject gareObject = array.get(i).getAsJsonObject();
				JsonObject fields = gareObject.get("fields").getAsJsonObject();
				JsonArray coordinates = gareObject.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray();
				
				station.setLatitude(coordinates.get(0).getAsDouble());
				station.setLongitude(coordinates.get(1).getAsDouble());
				station.setCode((int)Float.parseFloat(fields.get("code_uic").getAsString().substring(2, 8)));
				station.setName(fields.get("libelle_gare").getAsString());
				
				result.add(station);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return result.toArray(new Station[result.size()]);
	}
}
