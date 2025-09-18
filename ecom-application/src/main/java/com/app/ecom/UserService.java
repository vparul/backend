package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public User fetchSingleUser(Long id) {
       for (User user: users) {
           if (user.getId().equals(id)) {
               return user;
           }
       }
       return null;
    }
}
