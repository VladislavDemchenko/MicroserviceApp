package org.example.persons.service;

import com.netflix.discovery.DiscoveryClient;
import lombok.RequiredArgsConstructor;
import org.example.persons.dto.PersonDto;
import org.example.persons.repository.PersonRepository;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final DiscoveryClient discoveryClient;


//    public PersonDto getWithNoteById(Long personId){
//        personRepository.findById(personId).orElseThrow();
//        List<ServiceInstance> noteInstances = discoveryClient.getInstancesByVipAddressAndAppName("notes")

//    }
}
