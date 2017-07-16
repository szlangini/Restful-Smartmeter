package de.tub.ise.anwsys.clients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TestClient {
	
	public static void main(String[] args) throws IOException, UnirestException {
		
		// Measurements Array erstellen
		JSONArray zaehler = connectAndGet("/meters").getObject().getJSONArray("meters");
		System.out.println(zaehler);
		zaehler.forEach(z -> {
			String currentZaehler = (String) z;
			System.out.println(currentZaehler);
			getMetricsAndSendToRest(currentZaehler);
			
		});
	}
	
	// Connect to the node via url.
	public static JsonNode connectAndGet(String path){
		try {
			HttpResponse<JsonNode> response = Unirest.get("http://localhost:7878" + path).asJson();
			return response.getBody();// JSON Object
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject createMetricForRest(String name, Double value, int date){
		JSONObject newMetric = new JSONObject();
		newMetric.put("name", name);
		newMetric.put("value", value);
		newMetric.put("date", date);
		
		return newMetric;
	}
	
	public static List<JSONObject> getMetricsFromData(JSONObject data){
		int date = data.getInt("unixTimestamp"); // unixTimestamp ist von SMEMU Vorlage.
		List<JSONObject> metrics = new ArrayList<JSONObject>();
		// Immer metric ID + value bei "measurements"
		data.getJSONArray("measurements").forEach(m -> {
			JSONObject current = (JSONObject) m; // DATEN AUS DER JETZIGEN Metrik rauslesen
			String name = current.getString("metricId");
			Double value = current.getDouble("value"); 
			
			// Neues JSONObject zurückgeben mit diesen Daten.
			JSONObject newMetric = createMetricForRest(name, value, date);
			
			// Array hinzufügen
			metrics.add(newMetric);
		});     
		
		
		return metrics;
		
		
	}
	// Zaehler object erstellen
	public static JSONObject createZaehlerForRest(String name){
		// Zaehler erstellen mit dem namen
		JSONObject zaehler = new JSONObject();
		zaehler.put("name", name);
				
		return zaehler;
		
	}
	
	// Neuen Meter erstellen und aus
	public static void sendZaehlerToRest(String name){
		
		JSONObject zaehler = createZaehlerForRest(name);
		
		// http://unirest.io/java.html using unirest to post variable.  .asJson() to json
		try {
			// post to url with header => Json Data and body => my Object
		 Unirest.post("http://localhost:8080/zaehler").header("Content-Type", "application/json").body(zaehler).asJson();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	// Neuen Meter erstellen und aus
	public static void sendMetricToRest(String name, String metricId ,Double value, int timestamp ){
		// Zaehler erstellen mit dem namen
		JSONObject zaehler = createZaehlerForRest(name);
		JSONObject metric = new JSONObject();
		metric.put("name", metricId);
		metric.put("value", value);
		metric.put("ableseDatum", timestamp);
		metric.put("zaehler", name); // Soll ja auch den Zähler kennen.
		
		System.out.println(metric);
		// http://unirest.io/java.html using unirest to post variable.  .asJson() to json
		try {
			// post to url with header => Json Data and body => neues Metric Object
		 Unirest.post("http://localhost:8080/zaehler/"+name+"/metrics").header("Content-Type", "application/json").body(metric).asJson();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Methode um die nodes zu lesen und daraus Zähler/Metriken zu machen.
	public static void getMetricsAndSendToRest(String zaehler){
		
		// Zählerdaten holen und in Liste packen.
		List<JSONObject> data = getMetricsFromData(connectAndGet("/meters/" + zaehler +"/data").getObject());
		data.forEach(m -> {
			JSONObject datei = (JSONObject) m;
			// Sind schon JSONObjekte unserer art. rauslesen und schicken.
			int date = datei.getInt("date");
			String meterId = datei.getString("name");
			Double value = datei.getDouble("value");
			String name = zaehler;
			
			// Erstmal Meter Erstellen
			sendZaehlerToRest(zaehler);
			
			// Metricen für den Meter erstellen.
			sendMetricToRest(name, meterId, value, date);
			//List<JSONObject> metrics = getMetricsFromData(datei);
			
			
			//sendMetricToRest(datei.get("metricId"), datei.get("value"), datei.get("unixTimes"));
			
		});
	}
	

	

}
