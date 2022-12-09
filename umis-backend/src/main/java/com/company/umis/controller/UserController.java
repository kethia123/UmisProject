package com.company.umis.controller;

import com.company.umis.model.User;
import com.company.umis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable("id") long id){
        return userService.getUserById(id)
                .map(savedUser -> {

                    savedUser.setName(newUser.getName());
                    savedUser.setUsername(newUser.getUsername());
                    savedUser.setEmail(newUser.getEmail());

                    User updatedUser = userService.updateUser(savedUser);
                    return new ResponseEntity<>(updatedUser, HttpStatus.OK);

                })
//                .orElseThrow(()->new UserNotFoundException(id));
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id){

        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully!.", HttpStatus.OK);

    }

}
