package de.tub.ise.anwsys.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.tub.ise.anwsys.models.Meter;



public interface MeterRepository extends CrudRepository<Meter, String>  {
	List<Meter> findByName(String meter);
	Iterable<Meter> findAll();
}
