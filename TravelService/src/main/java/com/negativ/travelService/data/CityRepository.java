package com.negativ.travelService.data;

import com.negativ.travelService.data.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByOrderById();
    Optional<City> findByName(String name);
}
