package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private Long nextId = 1L;
    private final List<User> users = new ArrayList();

    public List<User> fetchAllUsers() {
        return users;
    }

    public void addUser(User user) {
        user.setId(nextId++);
        users.add(user);
    }

    public Optional<User> fetchSingleUser(Long id) {
        return users.stream().filter(user -> user.getId().equals((id))).findFirst();
    }
}
