package com.negativ.travelService.web;

import com.negativ.travelService.data.CityRepository;
import com.negativ.travelService.data.entities.City;
import com.negativ.travelService.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/api/city/{name}")
    City getCity(@PathVariable String name) {
        try {
            return repository.findByName(name)
                    .orElseThrow(() -> new NotFoundException(name));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "City " + name + " not found", e);
        }
    }

    @PostMapping("/api/city")
    City newCity(@RequestBody City newCity) {
        if (repository.findByName(newCity.getName()).isEmpty()) {
            return repository.save(newCity);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "City " + newCity.getName() + " already exists");
        }
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
