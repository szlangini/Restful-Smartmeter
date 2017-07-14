package de.tub.ise.anwsys.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
 
    // Persistent Fields:
    @Id
    @GeneratedValue
    Long id;
    private String name;
    private Date signingDate;
 
    // Constructors:
    public User() {
    }
 
    public User(String name) {
        this.name = name;
        this.signingDate = new Date(System.currentTimeMillis());
    }
 
    public String getName(){
    	return this.name;
    }
    // String Representation:
    @Override
    public String toString() {
        return name + " ("+signingDate +")";
    }
}