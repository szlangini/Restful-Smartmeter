package de.tub.ise.anwsys.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
@Entity
public class Counter implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -636121125128657026L;
	
    @Id @GeneratedValue
    Long id;
	//@Id
	private String meter;
	private String user;
	private String date;
	private double status;
	
	
	public String getMeter() {
		return meter;
	}

	public void setMeter(String meter) {
		this.meter = meter;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getStatus() {
		return status;
	}

	public void setStatus(double status) {
		this.status = status;
	}

	public Counter(String user, double status,String meter){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.user=user;
		this.status=status;
		this.meter=meter;
		date=dateFormat.format(new Date());
	}
	
	protected Counter(){
		
	}

	@Override
	public String toString() {
		return "Counter [user=" + user + ", date=" + date + ", status=" + status + ", meter=" + meter + "]";
	}

}
