package com.app.ecom;

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
    public ResponseEntity<List<User>> fetchAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
//        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> fetchSingleUser(@PathVariable Long id) {
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
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully");
//        return new ResponseEntity<>("User added successfully", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        Boolean isUpdated = userService.updateUser(user);
        if (isUpdated) return ResponseEntity.ok("User updated successfully");
        else return ResponseEntity.badRequest().body("User not found with ID " + user.getId());
    }
}
