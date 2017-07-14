package de.tub.ise.anwsys.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Zaehler implements Serializable {
    private static final long serialVersionUID = 1L;
 
    // Persistent Fields:
    @Id 
    String name;
        
    @OneToMany(fetch=FetchType.EAGER, mappedBy="zaehler", cascade=CascadeType.ALL)
    List<Metric> metrics = new ArrayList<Metric>();
    
    // Constructors:
    public Zaehler(){
    }
    
 
    public Zaehler(String name){
        this.name = name;
    }
     
    public String getName(){return this.name;}
	public void setName(String name){this.name = name;}
	
	public List<Metric> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<Metric> metrics) {
		this.metrics = metrics;
	}
	
    // String Representation:
    @Override
    public String toString() {
        return name;
    }
}