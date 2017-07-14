package de.tub.ise.anwsys.clients;

import java.io.IOException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TestClient {
	
	public static void main(String[] args) throws IOException, UnirestException {
		
		HttpResponse<String> response = Unirest.get("https://google.com").asString();
		System.out.println(String.format("Google's status code was: %d", response.getStatus()));

	}

}
