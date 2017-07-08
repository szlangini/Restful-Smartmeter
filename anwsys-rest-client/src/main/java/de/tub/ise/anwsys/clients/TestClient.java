package de.tub.ise.anwsys.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TestClient {
	
	public static void main(String[] args) throws IOException, UnirestException {
		
		
		

		
		addMeter("hallo",1.00,2.00);
		addMeter("YY567",100.00,200.00);
		addMeter("lololol",69.69,1.80);
		System.out.println(getMeterList());

		

		
		


		
		//HttpResponse<String> response = Unirest.get("http://192.168.99.100:8080/meters?meter=XX123").asString();
		//response = Unirest.get("http://192.168.99.100:8080/meterlist").asString();
//		HttpResponse<JsonNode> request =Unirest.post("http://httpbin.org/post")
//				  .queryString("name", "Mark")
//				  .field("last", "Polo")
//				  .asJson();
//		System.out.println(request.getBody().toString());
//		HttpResponse<String> response = Unirest.get("http://httpbin.org/get").asString();
//		System.out.println(response.getBody());
//		HttpResponse<JsonNode> q=Unirest.post("http://192.168.99.100:8080/meters")
//		  .queryString("meter", "Mark")
//		  //.field("last", "Polo")
//		  .asJson();
//		System.out.println(q.getBody().toString());
		//HttpResponse<String> response = Unirest.get("https://google.com").asString();
		//System.out.println(String.format("Google's status code was: %d", response.getStatus()));
		//System.out.println(response.getBody());
		//System.out.println(String.format("Google's status code was: %d", response.getStatus()));


		

	}
	
	public static void addMeter(String name, double volt, double amp){
		String json="{\"name\":" +"\""+name+"\",\"voltage\":"+volt+",\"amp\":"+amp+"}";
		String requestUrl="http://192.168.99.100:8080/meterlist";
		sendPostRequest(requestUrl, json);
		
	}
	
	public static String  getMeterList() throws UnirestException{

		
		return  Unirest.get("http://192.168.99.100:8080/meterlist").asString().getBody();
	}
	
	

	public static void sendPostRequest(String requestUrl, String json) {
	    try {
	        URL url = new URL(requestUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	        writer.write(json);
	        writer.close();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuffer jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
	        connection.disconnect();
	    } catch (Exception e) {
	            throw new RuntimeException(e.getMessage());
	    }
	    //return "lol";//jsonString.toString();
	}
	


}
