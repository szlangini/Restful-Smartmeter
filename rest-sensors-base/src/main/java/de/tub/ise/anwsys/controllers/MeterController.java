package de.tub.ise.anwsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.tub.ise.anwsys.models.Meter;
import de.tub.ise.anwsys.repos.MeterRepository;


@RestController
public class MeterController {
	
	@Autowired
	MeterRepository meterRep; 
	
//	
//	@RequestMapping(method=RequestMethod.GET)
//	public String answerAndRegister(@RequestParam(value = "meter", defaultValue = "DefaulMeter") String meter,
//									@RequestParam(value = "amp", defaultValue = "100.00") double amp,
//									@RequestParam(value = "volt", defaultValue = "100.00") double volt) {
//		
//		List<Meter> foundMeters =(List<Meter>) meterRep.findAll();
//		/*if (!foundMeters.isEmpty()&&meterRep.findByName(meter).isEmpty()) {
//			Meter m = new Meter(meter,amp,volt);
//			meterRep.save(m);
//			String meters="";
//			for(Meter mm:foundMeters){
//				meters=meters+mm.toString();
//			}
//			return String.format(meters);
//			
//		}*/
//		if (!foundMeters.isEmpty()&&meterRep.findByName(meter).isEmpty()) {
//			Meter m = new Meter(meter,amp,volt);
//			meterRep.save(m);
//			return String.format("New Meter %s registered!", m.getName());
//		}
//		if (!foundMeters.isEmpty()) {
//			String meters="";
//			for(Meter m:foundMeters){
//				meters=meters+m.toString();
//			}
//			return String.format(meters);
//		} else {
//			Meter m = new Meter(meter,amp,volt);
//			meterRep.save(m);
//			return String.format("New Meter %s registered!", m.getName());
//		}
//	}
	@RequestMapping(method=RequestMethod.GET, path="/meter")
	public ResponseEntity<List<Meter>> getAllMeters(){
		meterRep.save(new Meter("TESTMETER",1.11, 2.22));
		meterRep.save(new Meter("TESTMETER2",1.11, 2.22));
		meterRep.save(new Meter("TESTMETER3",1.11, 2.22));
		if(meterRep.count()==0){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>((List<Meter>) meterRep.findAll(),HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/meter/{id}")
	public ResponseEntity<Meter> getMeter(@PathVariable String id /*Long id* id muss noch zu meter hinzugefügt werden*/) {
		
		List<Meter> foundMeters = meterRep.findByName(id);
		if(foundMeters.isEmpty()){
			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);//eigentlich NOT_FOUND
		}
		
	return new ResponseEntity<>(foundMeters.get(0),HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/meter")
	public ResponseEntity<Meter> createMeter(Meter in){
		if(in==null){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		meterRep.save(in);
		return new ResponseEntity<>(in,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.PUT, path="/meter/{id}")
	public ResponseEntity<Meter> updateMeter(@PathVariable String id){
		List<Meter> meter=meterRep.findByName(id);
		return null;
	}
	
	
}
