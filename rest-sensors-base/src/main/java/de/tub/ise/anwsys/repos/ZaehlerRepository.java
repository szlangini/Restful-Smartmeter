package de.tub.ise.anwsys.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.tub.ise.anwsys.models.Zaehler;

public interface ZaehlerRepository extends CrudRepository<Zaehler, String> {
	List<Zaehler> findByName(String name);
	Iterable<Zaehler> findAll();
}
