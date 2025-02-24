package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.model.Manager;
import com.booleanuk.simpleapi.repository.ManagerRepository;
import com.booleanuk.simpleapi.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("managers")
public class ManagerController {
    @Autowired
    private ManagerRepository managerRepository;

    @GetMapping
    public ResponseEntity<ManagerListResponse> getAllManagers(){
        ManagerListResponse managerListResponse = new ManagerListResponse();
        managerListResponse.set(this.managerRepository.findAll());
        return ResponseEntity.ok(managerListResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Manager request) {
        ManagerResponse managerResponse = new ManagerResponse();
        try{
            managerResponse.set(this.managerRepository.save(request));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(managerResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateManager(@PathVariable int id, @RequestBody Manager manager) {
        Manager managerToUpdate = null;

        try {
            managerToUpdate = this.managerRepository.findById(id).orElse(null);
            if(managerToUpdate == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            managerToUpdate.setFirstName(manager.getFirstName());
            managerToUpdate.setLastName(manager.getLastName());
            managerToUpdate.setAge(manager.getAge());

            managerToUpdate = this.managerRepository.save(managerToUpdate);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        ManagerResponse managerResponse = new ManagerResponse();
        managerResponse.set(managerToUpdate);

        return new ResponseEntity<>(managerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteManager(@PathVariable int id) {
        Manager managerToDelete = this.managerRepository.findById(id).orElse(null);

        if(managerToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.managerRepository.delete(managerToDelete);
        ManagerResponse managerResponse = new ManagerResponse();
        managerResponse.set(managerToDelete);

        return ResponseEntity.ok(managerResponse);
    }
}
