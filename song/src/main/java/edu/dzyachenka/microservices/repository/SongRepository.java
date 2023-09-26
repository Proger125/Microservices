package edu.dzyachenka.microservices.repository;

import edu.dzyachenka.microservices.model.SongModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends CrudRepository<SongModel, Integer> {
}
