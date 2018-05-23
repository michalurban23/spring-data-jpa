package com.michalurban23.springdatajpa.match;

import com.michalurban23.springdatajpa.team.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface MatchRepository extends CrudRepository<Match, Integer> {

    @Query("SELECT (m.goalsHomeTeam + m.goalsAwayTeam) AS goals, m FROM Match m ORDER BY goals DESC ")
    Page<Object[]> findMostGoals(Pageable page);

    @Query("SELECT m FROM Match m WHERE m.homeTeam = :team OR m.awayTeam = :team")
    Page<Match> getMultipleMatches(Pageable page, @Param("team") Team team);

    Iterable<Match> findDistinctByHomeTeamIs(Team team);

    Iterable<Match> findDistinctByAwayTeamIs(Team team);

    Iterable<Match> findDistinctByDateIsAfter(LocalDate date);
}
