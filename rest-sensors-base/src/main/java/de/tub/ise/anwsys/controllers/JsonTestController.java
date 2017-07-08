package de.tub.ise.anwsys.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.tub.ise.anwsys.models.Meter;
import de.tub.ise.anwsys.repos.MeterRepository;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
@RestController
public class JsonTestController {
	
	@Autowired
	MeterRepository meterRep; 
	
	/*@RequestMapping(value = "/")
	public ResponseEntity<Meter> get() {

	    Meter meter = new Meter("XX12345678",50.00,100.00);

	    return new ResponseEntity<Meter>(meter, HttpStatus.OK);
	}*/

	  
	    @RequestMapping(method=RequestMethod.GET, path="/meterlist")
	    @Produces("application/json")
	    public List<Meter> getMeters() {//sendet alle vorhanden meter als json raus

	        /*List<Meter> meters = new ArrayList<>();
	        meters.add(new Meter("XX",4.00,5.00));
	        meters.add(new Meter("AA",3.00,78.00));
	        meters.add(new Meter("BB",7.00,45.00));
	        meters.add(new Meter("CC",9.00,9.00));*/
	    	List<Meter> meters = (List<Meter>) meterRep.findAll();

	        return meters;

	    }
//	@Consumes("application/json")
//	@RequestMapping(value = "/meterlist", method = RequestMethod.POST)
//	public String update(List<Meter> meters) {
//
//	    if (meters != null) {
//	    	Meter m = new Meter("MOFO",1.00,1.00);
//			meterRep.save(m);
//	    }
//
//	    return "done";
//	}
	    
	    @Consumes("application/json")
	    @RequestMapping(value = "/meterlist", method = RequestMethod.POST)
	    public ResponseEntity<Meter> update(@RequestBody Meter meter) {

	        if (meter != null) {
	        	if(meterRep.findByName(meter.getName()).size()>0){
	        		meterRep.save(meter);
	        	}
	        	else  meterRep.save(meter);
	        }

	        // TODO: call persistence layer to update
	        return new ResponseEntity<Meter>(meter, HttpStatus.OK);
	    }
}
