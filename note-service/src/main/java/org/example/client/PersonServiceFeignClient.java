package org.example.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "persons-service")
public interface PersonServiceFeignClient {

    @GetMapping("/api/persons/{id}")
    @CircuitBreaker(name = "persons-service", fallbackMethod = "defaultPerson")
    PersonDto getById(@PathVariable Long id);

    default PersonDto defaultPerson(Long personId, Throwable throwable){
          return new PersonDto(personId, "HIDDEN", "HIDDEN");
   }
}
