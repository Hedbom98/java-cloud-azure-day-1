package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.model.League;
import com.booleanuk.simpleapi.model.Manager;
import com.booleanuk.simpleapi.model.Player;
import com.booleanuk.simpleapi.model.Team;
import com.booleanuk.simpleapi.repository.LeagueRepository;
import com.booleanuk.simpleapi.repository.ManagerRepository;
import com.booleanuk.simpleapi.repository.TeamRepository;
import com.booleanuk.simpleapi.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("teams")
public class TeamController {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @GetMapping
    public ResponseEntity<TeamListResponse> getAllPlayers(){
        TeamListResponse teamListResponse = new TeamListResponse();
        teamListResponse.set(this.teamRepository.findAll());
        return ResponseEntity.ok(teamListResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{lId}/{mId}")
    public ResponseEntity<Response<?>> create(@RequestBody Team request, @PathVariable("mId") int mId, @PathVariable("lId") int lId) {
        TeamResponse teamResponse = new TeamResponse();

        Manager manager = this.managerRepository.findById(mId).orElse(null);
        League league = this.leagueRepository.findById(lId).orElse(null);


        try{
            if(manager == null || league == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            request.setManager(manager);
            request.setLeague(league);

            teamResponse.set(this.teamRepository.save(request));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(teamResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}/{lId}/{mId}")
    public ResponseEntity<Response<?>> updateTeam(@PathVariable int id, @PathVariable("mId") int mId, @PathVariable("lId") int lId, @RequestBody Team team) {
        Team teamToUpdate = null;

        try {
            teamToUpdate = this.teamRepository.findById(id).orElse(null);
            Manager manager = this.managerRepository.findById(mId).orElse(null);
            League league = this.leagueRepository.findById(lId).orElse(null);
            if(teamToUpdate == null || manager == null || league == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            teamToUpdate.setName(team.getName());
            teamToUpdate.setFounded(team.getFounded());
            teamToUpdate.setManager(manager);
            teamToUpdate.setLeague(league);

            teamToUpdate = this.teamRepository.save(teamToUpdate);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        TeamResponse teamResponse = new TeamResponse();
        teamResponse.set(teamToUpdate);

        return new ResponseEntity<>(teamResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteTeam(@PathVariable int id) {
        Team teamToDelete = this.teamRepository.findById(id).orElse(null);

        if(teamToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.teamRepository.delete(teamToDelete);
        TeamResponse teamResponse = new TeamResponse();
        teamResponse.set(teamToDelete);

        return ResponseEntity.ok(teamResponse);
    }
}
