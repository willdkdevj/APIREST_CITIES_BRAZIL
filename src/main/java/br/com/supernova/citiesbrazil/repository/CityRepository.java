package br.com.supernova.citiesbrazil.repository;

import br.com.supernova.citiesbrazil.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

}
