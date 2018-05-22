package com.michalurban23.springdatajpa.team;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Integer> {

    Team findBestTeam();
    Iterable<Team> findDistinctTeamsByNameIsContaining(String name);

}
