package de.tub.ise.anwsys.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.tub.ise.anwsys.models.Counter;


public interface CounterRepository extends CrudRepository<Counter,String> {
	Iterable<Counter> findAll();
	List<Counter> findByMeter(String meter);
}
