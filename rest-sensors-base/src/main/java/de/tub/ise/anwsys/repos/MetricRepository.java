package de.tub.ise.anwsys.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.tub.ise.anwsys.models.Metric;
import de.tub.ise.anwsys.models.Zaehler;

public interface MetricRepository extends CrudRepository<Metric, Long> {
	
	Iterable<Metric> findAll();
	List<Metric> findByName(String zaehler);
}
