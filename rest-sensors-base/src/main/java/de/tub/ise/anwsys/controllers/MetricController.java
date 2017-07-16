package de.tub.ise.anwsys.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.tub.ise.anwsys.models.Metric;
import de.tub.ise.anwsys.models.Zaehler;
import de.tub.ise.anwsys.repos.MetricRepository;
import de.tub.ise.anwsys.repos.ZaehlerRepository;

@RestController
public class MetricController {

	@Autowired
	ZaehlerRepository zRepository;
	
	@Autowired
	MetricRepository mRepository;
	
	@RequestMapping(method=RequestMethod.GET, path="/zaehler/{name}/metrics" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getMetricsForMeter(@PathVariable("name") String name){
		List<Zaehler> zaehler = zRepository.findByName(name);
		if (!zaehler.isEmpty()){
//			return new MetricCollection(zaehler.get(0).getMetrics().stream().map(SingleMetric::new).toArray());
			return new MetricCollection(zaehler.get(0).getMetrics().stream().map(m -> m.getName()).distinct().toArray());

		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
		}
		
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/zaehler/{name}/metrics/{metric}" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getMetricInMeter(@PathVariable("name") String name, @PathVariable("metric") String metric) {
		List<Zaehler> zaehler = zRepository.findByName(name);
		if (zaehler.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
		}
		return new MetricCollection(zaehler.get(0)
				.getMetrics()
				.stream()
				.filter(m -> m.getName().toLowerCase().equals(metric.toLowerCase()))
				.map(m -> new SingleAverageMetric(m))
				.collect(Collectors.groupingBy(SingleAverageMetric::getInterval, Collectors.reducing((a, b) -> {
					a.add(b.getSum());
					return a;
				})))
				.entrySet()
				.stream()
				.map(e -> e.getValue().get())
				.toArray());
	}

	@RequestMapping(method=RequestMethod.POST, path="/zaehler/{name}/metrics" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object createMetricForMeter(@PathVariable("name") String name, @RequestBody SingleMetric singleMetric) {
		List<Zaehler> zaehler = zRepository.findByName(name);
		if (zaehler.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
		}
		Metric metric = new Metric(singleMetric.getName(), singleMetric.getValue(), zaehler.get(0));
		if (singleMetric.getDate() != null) {
			metric.setAbleseDatum(singleMetric.getDate());
		}
		mRepository.save(metric);
		singleMetric.setDate(metric.getAbleseDatum());
		return ResponseEntity.status(HttpStatus.CREATED).body(singleMetric);
	}
	
}

class SingleMetric{
	String name;
	Date date;
	Double value;

	public SingleMetric(){}
	public SingleMetric(Metric m) {
		this.date = m.getAbleseDatum();
		this.name = m.getName();
		this.value = m.getValue();
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}

class SingleAverageMetric extends SingleMetric {
	int interval;
	int count = 1;
	Double sum;
	
	public SingleAverageMetric(){}
	public SingleAverageMetric(Metric m) {
		super(m);
		this.sum = this.value;
		this.interval = m.getInterval();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(this.date);
		calendar.set(Calendar.HOUR_OF_DAY, this.interval/4);
		calendar.set(Calendar.MINUTE, (this.interval%4) * 15);
		calendar.set(Calendar.MILLISECOND, 0);
		this.date = calendar.getTime();
	}
	@JsonIgnore
	public int getInterval() {
		return interval;
	}
	@JsonIgnore
	public Double getValue() {
		return null;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public void add(Double v) {
		this.sum += v;
		this.count++;
	}
	public Double getAverage() {
		return sum/count;
	}
	@JsonIgnore
	public Double getSum() {
		return this.sum;
	}
	public int getCount() {
		return count;
	}
	
}

class MetricCollection{
	Object[] metrics;

	public MetricCollection(Object[] metrics) {
		this.metrics = metrics;
	}

	public Object[] getMetrics() {
		return metrics;
	}

	public void setMetrics(Object[] meters) {
		this.metrics = meters;
	}	
	
}