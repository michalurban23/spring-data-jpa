package com.michalurban23.springdatajpa.match;

import com.michalurban23.springdatajpa.team.Team;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Team homeTeam;

    @ManyToOne
    private Team awayTeam;

    private LocalDate date;
    private int goalsHomeTeam;
    private int goalsAwayTeam;

    public Match() {
    }

    public Match(LocalDate date, Team homeTeam, Team awayTeam, int goalsHomeTeam, int goalsAwayTeam) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.goalsHomeTeam = goalsHomeTeam;
        this.goalsAwayTeam = goalsAwayTeam;
    }

    public Integer getHomeTeamPoints() {

        return goalsHomeTeam == goalsAwayTeam ? 1 : (goalsHomeTeam > goalsAwayTeam ? 3 : 0);
    }

    public Integer getAwayTeamPoints() {

        return goalsHomeTeam == goalsAwayTeam ? 1 : (goalsHomeTeam > goalsAwayTeam ? 0 : 3);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(int goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public int getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(int goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }

    @Override
    public String toString() {
        return "Match #" + id +
                ", " + date + ", " +
                homeTeam.getFullName() + " " + goalsHomeTeam + ":" + goalsAwayTeam + " " + awayTeam.getFullName();
    }
}
