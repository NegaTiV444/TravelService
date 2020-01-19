package com.negativ.travelService.web;

import com.negativ.travelService.data.CityRepository;
import com.negativ.travelService.data.entities.City;
import com.negativ.travelService.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = "application/json")
public class CityController {

    private CityRepository repository;

    @Autowired
    public void setRepository(CityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/city")
    List<City> getAllCities() {
        return repository.findAllByOrderById();
    }

    @GetMapping("/api/city/{id}")
    City getCity(@PathVariable long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(Long.toString(id)));
    }

    @PostMapping("/api/city")
    City newCity(@RequestBody City newCity) {
        return repository.save(newCity);
    }

    @DeleteMapping("/api/city/{id}")
    void deleteCity(@PathVariable long id) {
        repository.deleteById(id);
    }

    @PutMapping("/api/city")
    City updateCity(@RequestBody City updatedCity) {
        return repository.save(updatedCity);
    }
}
