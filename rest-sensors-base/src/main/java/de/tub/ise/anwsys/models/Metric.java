package de.tub.ise.anwsys.models;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Metric implements Serializable {
    private static final long serialVersionUID = 1L;
 
    // Persistent Fields:
    @Id 
    @GeneratedValue
    Long id;
    
    String name;
    
    Double value;
    
    @ManyToOne
    Zaehler zaehler;
    
    Date ableseDatum;
    // Constructors:
    public Metric(){
    }
    
 
    public Metric(String name, Double value, Zaehler zaehler){
        this.name = name;
        this.value = value;
        this.ableseDatum = new Date(System.currentTimeMillis());
        this.zaehler = zaehler;
    }
 
    
	
	
    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Double getValue() {
		return value;
	}


	public void setValue(Double value) {
		this.value = value;
	}


	public Zaehler getZaehler() {
		return zaehler;
	}


	public void setZaehler(Zaehler zaehler) {
		this.zaehler = zaehler;
	}


	public Date getAbleseDatum() {
		return ableseDatum;
	}


	public void setAbleseDatum(Date ableseDatum) {
		this.ableseDatum = ableseDatum;
	}

	@SuppressWarnings("deprecation")
	public int getInterval() {
		return ((ableseDatum.getHours()*60) + ableseDatum.getMinutes()) / 15;
	}

	// String Representation:
    @Override
    public String toString() {
        return name;
    }
}