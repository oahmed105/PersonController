package com.example.PersonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    private PersonRepo personRepo;

    @PostMapping(value ="/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person p) {
//        Person person = personRepo.save(p);

        return new ResponseEntity<>(personRepo.save(p), HttpStatus.CREATED);
    }

    @GetMapping(value = "/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {

        if (personRepo.findById(id).isPresent()){
            return new ResponseEntity<>(personRepo.findById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/people")
    public ResponseEntity<List<Person>> getPersonList() {
        try {
            List<Person> personList = new ArrayList<>();
            personRepo.findAll().forEach(personList::add);

            if (personList.isEmpty()){
                return new ResponseEntity<>(personList, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(personList, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/people/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person p) {
//        Optional<Person> oldPerson = personRepo.findById(id);

        if (personRepo.findById(id).isPresent()){
//            Person newPerson = personRepo.findById(id).get();
//            newPerson.setFirstName(p.getFirstName());
//            newPerson.setLastName(p.getLastName());
//
//            Person person = personRepo.save(newPerson);
            p.setId(id);
            return new ResponseEntity<>(personRepo.save(p), HttpStatus.OK);
        }
        return createPerson(p);
    }

    @DeleteMapping(value = "/people/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable Long id) {
        personRepo.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
