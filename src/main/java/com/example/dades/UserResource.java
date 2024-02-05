package com.example.dades;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/v0/users";
    @Autowired
    UserController userController;
    @GetMapping
    public ResponseEntity<List<UserDto>> users() {
        return new ResponseEntity<>(userController.readAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> user(@PathVariable Integer id) {
        return new ResponseEntity<>(userController.getUserById(id), HttpStatus.OK);
    }
    @GetMapping("/{id}/email")
    public ResponseEntity<Map<String,String>> email(@PathVariable Integer id) {
        return new ResponseEntity<>(Collections.singletonMap("email", userController.getUserById(id).getEmail()), HttpStatus.OK);
    }
    @PostMapping    
    public ResponseEntity<UserDto> newUser(@RequestBody User user) {
        return ResponseEntity.ok(userController.addUser(user));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userController.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> putUser(@PathVariable Integer id, @RequestBody User user) {
        return ResponseEntity.ok(userController.putUser(id,user));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable Integer id, @RequestBody JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException {
        return ResponseEntity.ok(userController.pathUser(id,jsonPatch));
    }
}
