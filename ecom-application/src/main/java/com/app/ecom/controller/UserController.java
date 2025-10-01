package com.app.ecom.controller;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.User;
import com.app.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> fetchAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
//        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> fetchSingleUser(@PathVariable Long id) {
        return userService.fetchSingleUser(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
//        Optional<User> user = userService.fetchSingleUser(id);
//        if (user != null) {
//            return ResponseEntity.ok(user);
////            return new ResponseEntity<>(user, HttpStatus.OK);
//        } else {
//            return ResponseEntity.notFound().build();
////            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added successfully");
//        return new ResponseEntity<>("User added successfully", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        Boolean isUpdated = userService.updateUser(id, userRequest);
        if (isUpdated) return ResponseEntity.ok("User updated successfully");
        else return ResponseEntity.badRequest().body("User not found with ID " + id);
    }
}
