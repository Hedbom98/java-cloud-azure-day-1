package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.model.League;
import com.booleanuk.simpleapi.model.Manager;
import com.booleanuk.simpleapi.model.Player;
import com.booleanuk.simpleapi.model.Team;
import com.booleanuk.simpleapi.repository.PlayerRepository;
import com.booleanuk.simpleapi.repository.TeamRepository;
import com.booleanuk.simpleapi.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("players")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping
    public ResponseEntity<PlayerListResponse> getAllPlayers(){
        PlayerListResponse playerListResponse = new PlayerListResponse();
        playerListResponse.set(this.playerRepository.findAll());
        return ResponseEntity.ok(playerListResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{tId}")
    public ResponseEntity<Response<?>> create(@RequestBody Player request, @PathVariable("tId") int tId) {
        PlayerResponse playerResponse = new PlayerResponse();
        try{
            Team team = this.teamRepository.findById(tId).orElse(null);
            if(team == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            request.setTeam(team);

            playerResponse.set(this.playerRepository.save(request));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(playerResponse, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getByID(@PathVariable("id") int id) {
        PlayerResponse playerResponse = new PlayerResponse();
        try{
            Player player = this.playerRepository.findById(id).orElse(null);
            if(player == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            playerResponse.set(player);
            return ResponseEntity.ok(playerResponse);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("{id}/{tId}")
    public ResponseEntity<Response<?>> updatePlayer(@PathVariable int id, @PathVariable("tId") int tId, @RequestBody Player player) {
        Player playerToUpdate = null;

        try {
            playerToUpdate = this.playerRepository.findById(id).orElse(null);
            Team team = this.teamRepository.findById(tId).orElse(null);
            if(playerToUpdate == null || team == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            playerToUpdate.setFirstName(player.getFirstName());
            playerToUpdate.setLastName(player.getLastName());
            playerToUpdate.setAge(player.getAge());
            playerToUpdate.setParentClub(player.getParentClub());
            playerToUpdate.setTeam(team);

            playerToUpdate = this.playerRepository.save(playerToUpdate);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.set(playerToUpdate);

        return new ResponseEntity<>(playerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deletePlayer(@PathVariable int id) {
        Player playerToDelete = this.playerRepository.findById(id).orElse(null);

        if(playerToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.playerRepository.delete(playerToDelete);
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.set(playerToDelete);

        return ResponseEntity.ok(playerResponse);
    }
}
