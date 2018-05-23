package com.michalurban23.springdatajpa.team;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Integer> {

    Iterable<Team> findDistinctTeamsByNameIsContaining(String name);
}
