package de.tub.ise.anwsys.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.tub.ise.anwsys.models.Counter;
import de.tub.ise.anwsys.repos.CounterRepository;

@RestController
public class ReadingController {
	@Autowired
	CounterRepository readingRep;
	
	  
    @RequestMapping(method=RequestMethod.GET, path="/readings")
    @Produces("application/json")
    public List<Counter> getReadings(@RequestParam(value = "meter") String meter) { 

    	readingRep.save(new Counter("DefaulUser",1234.45,"XX12345678"));
    	readingRep.save(new Counter("DefaulUser",3234.45,"XX12345678"));
    	readingRep.save(new Counter("DefaulUser",6234.45,"XX12345678"));
  
    	List<Counter> readings = (List<Counter>) readingRep.findByMeter(meter);

        return readings;

    }

}
