package edu.dzyachenka.microservices.repository;

import edu.dzyachenka.microservices.model.Mp3Model;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mp3Repository extends CrudRepository<Mp3Model, Integer> {
}
