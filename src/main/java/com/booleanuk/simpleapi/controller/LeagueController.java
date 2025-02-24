package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.model.League;
import com.booleanuk.simpleapi.repository.LeagueRepository;
import com.booleanuk.simpleapi.responses.ErrorResponse;
import com.booleanuk.simpleapi.responses.LeagueListResponse;
import com.booleanuk.simpleapi.responses.LeagueResponse;
import com.booleanuk.simpleapi.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin
@RestController
@RequestMapping("leagues")
public class LeagueController {
    @Autowired
    private LeagueRepository leagueRepository;

    @GetMapping
    public ResponseEntity<LeagueListResponse> getAllLeagues(){
        LeagueListResponse leagueListResponse = new LeagueListResponse();
        leagueListResponse.set(this.leagueRepository.findAll());
        return ResponseEntity.ok(leagueListResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody League request) {
        LeagueResponse leagueResponse = new LeagueResponse();
        try{
            leagueResponse.set(this.leagueRepository.save(request));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(leagueResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateLeague(@PathVariable int id, @RequestBody League league) {
        League leagueToUpdate = null;

        try {
            leagueToUpdate = this.leagueRepository.findById(id).orElse(null);
            if(leagueToUpdate == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            leagueToUpdate.setName(league.getName());
            leagueToUpdate.setCountry(league.getCountry());

            leagueToUpdate = this.leagueRepository.save(leagueToUpdate);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        LeagueResponse leagueResponse = new LeagueResponse();
        leagueResponse.set(leagueToUpdate);

        return new ResponseEntity<>(leagueResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteLeague(@PathVariable int id) {
        League leagueToDelete = this.leagueRepository.findById(id).orElse(null);

        if(leagueToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.leagueRepository.delete(leagueToDelete);
        LeagueResponse leagueResponse = new LeagueResponse();
        leagueResponse.set(leagueToDelete);

        return ResponseEntity.ok(leagueResponse);
    }
}
