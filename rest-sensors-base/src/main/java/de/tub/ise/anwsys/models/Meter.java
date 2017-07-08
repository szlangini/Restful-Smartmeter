package de.tub.ise.anwsys.models;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_EMPTY)
@Entity
public class Meter implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7040167501757842440L;
	
	@Id
	String name;
	double voltage;
	double amp;
	//@JsonIgnore
	//ArrayList<Counter>Readings;
	
	protected Meter(){
		
	}
	
	public Meter(String name,Double amp, Double volt){
		this.name=name;
		this.amp=amp;
		this.voltage=volt;
		//Readings=new ArrayList<Counter>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getVoltage() {
		return voltage;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

	public double getAmp() {
		return amp;
	}

	public void setAmp(double amp) {
		this.amp = amp;
	}

	@Override
	public String toString() {
		return "Meter [name=" + name + ", voltage=" + voltage + ", amp=" + amp + "]";
	}
}
