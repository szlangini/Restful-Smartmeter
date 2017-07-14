package de.tub.ise.anwsys.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.tub.ise.anwsys.models.User;
import de.tub.ise.anwsys.models.Zaehler;
import de.tub.ise.anwsys.repos.ZaehlerRepository;

@RestController
public class ZaehlerController {

	@Autowired
	ZaehlerRepository repository;
	
	// Alle Zähler
	@RequestMapping(method=RequestMethod.GET, path="/zaehler", produces = MediaType.APPLICATION_JSON_VALUE)
	public ZaehlerWrapper listZaehler(){
		ZaehlerWrapper result = new ZaehlerWrapper();
		List<String> allZaehler = new ArrayList<String>();
		repository.findAll().forEach(z -> allZaehler.add(z.getName()));
		result.setZaehler(allZaehler);
		return result;	
	}
	
	// Zähler hinzufügen
	@RequestMapping(method=RequestMethod.POST, path="/zaehler", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object addZaehler(@RequestBody Zaehler z){
		repository.save(z);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ZaehlerNameWrapper(z.getName()));
		
	}
	// Einen bestimmten Zähler finden
	@RequestMapping(method=RequestMethod.GET, path="/zaehler/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object specificZaehler(@PathVariable("name") String name){
		
		List<Zaehler> zaehler = repository.findByName(name);
		if (zaehler.get(0) != null){
			return new ZaehlerNameWrapper(zaehler.get(0).getName());
		}
		else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NIL");
		}
		
	}
		
}
// CLASSES FOR DISPLAY OF DATA

	class ZaehlerWrapper{
		List<String> zaehlerNames;

		public List<String> getZaehler() {
			return zaehlerNames;
		}

		public void setZaehler(List<String> zaehlerNames) {
			this.zaehlerNames = zaehlerNames;
		}
		
	}
	class ZaehlerNameWrapper{
		String name;

		public ZaehlerNameWrapper(String name){
			this.name = name;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}