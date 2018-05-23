package com.michalurban23.springdatajpa;

import com.michalurban23.springdatajpa.match.Match;
import com.michalurban23.springdatajpa.match.MatchRepository;
import com.michalurban23.springdatajpa.team.Team;
import com.michalurban23.springdatajpa.team.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner test(TeamRepository teamRepository, MatchRepository matchRepository) {

	    return (args) -> {
	        fillDatabase(teamRepository, matchRepository);
            runQueries(teamRepository, matchRepository);
        };
    }

    private static void fillDatabase(TeamRepository teamRepository, MatchRepository matchRepository) {

	    Team team1 = new Team("Wisła", "Polska", "Kraków");
        Team team2 = new Team("FC Bayern", "Germany", "Munich");
        Team team3 = new Team("FC", "Spain", "Barcelona");
        Team team4 = new Team("Real", "Spain", "Madrid");
        Team team5 = new Team("Juventus", "Italy", "Turin");
        Team team6 = new Team("Legia", "Poland", "Warszawa");
        teamRepository.saveAll(Arrays.asList(team1, team2, team3, team4, team5, team6));

        Match match1 = new Match(LocalDate.of(2018, 1, 1), team1, team2, 1, 2);
        Match match2 = new Match(LocalDate.of(2017, 1, 2), team1, team3, 1, 0);
        Match match3 = new Match(LocalDate.of(2018, 2, 3), team2, team4, 2, 1);
        Match match4 = new Match(LocalDate.of(2017, 3, 1), team3, team5, 2, 0);
        Match match5 = new Match(LocalDate.of(2018, 6, 11), team4, team6, 3, 1);
        Match match6 = new Match(LocalDate.of(2017, 8, 12), team5, team1, 3, 2);
        Match match7 = new Match(LocalDate.of(2017, 3, 9), team6, team2, 4, 1);
        Match match8 = new Match(LocalDate.of(2018, 11, 21), team1, team3, 4, 3);
        Match match9 = new Match(LocalDate.of(2018, 10, 1), team2, team5, 0, 0);
        matchRepository.saveAll(Arrays.asList(match1, match2, match3, match4, match5, match6, match7, match8, match9));
    }

    private static void runQueries(TeamRepository teamRepository, MatchRepository matchRepository) {


        System.out.println("\nFind All Teams:");
        for (Team t: teamRepository.findAll()) {
            System.out.println(t);
        }


        System.out.println("\nFind All Matches:");
        for (Match m: matchRepository.findAll()) {
            System.out.println(m);
        }


        System.out.println("\nBest Team:");
        Team topTeam = null;
        int topScore = 0;
        for (Team t: teamRepository.findAll()) {
            int score = 0;
            for (Match m: matchRepository.findDistinctByHomeTeamIs(t)) {
                score += m.getHomeTeamPoints();
            }
            for (Match m: matchRepository.findDistinctByAwayTeamIs(t)) {
                score += m.getAwayTeamPoints();
            }
            if (score > topScore) {
                topTeam = t;
                topScore = score;
            }
        }
        System.out.println(topTeam.getFullName() + " -> " + topScore + " points");


        System.out.println("\nTeams With FC in Name:");
        for (Team t: teamRepository.findDistinctTeamsByNameIsContaining("FC")) {
            System.out.println(t);
        }


        System.out.println("\nMatches With Most Goals");
        for (Object[] result: matchRepository.findMostGoals(PageRequest.of(0, 3))) {
            System.out.println(result[1] + " -> " + result[0] + " goals");
        }


        System.out.println("\nTeams that played in more than one match");
        for (Team t: teamRepository.findAll()) {
            long games = matchRepository.getMultipleMatches(PageRequest.of(0, 10), t).getTotalElements();
            if (games > 1) {
                System.out.println(t.getFullName() + ", " + games + " games");
            }
        }


        System.out.println("\nTeams and their total goal count");
        for (Team t: teamRepository.findAll()) {
            int goals = 0;
            for (Match m: matchRepository.findDistinctByHomeTeamIs(t)) {
                goals += m.getGoalsHomeTeam();
            }
            for (Match m: matchRepository.findDistinctByAwayTeamIs(t)) {
                goals += m.getGoalsAwayTeam();
            }
            System.out.println(t.getFullName() + ", " + goals + " goals");
        }

        System.out.println("\nMatches played last year");
        for (Match m: matchRepository.findDistinctByDateIsAfter(LocalDate.now().minusYears(1))) {
            System.out.println(m);
        }

    }
}
