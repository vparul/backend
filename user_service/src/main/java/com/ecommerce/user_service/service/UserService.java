package com.app.ecom.service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream().map(this::mapUserToUserResponse).toList();
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        userRepository.save(getUserFromUserRequest(user, userRequest));
    }

    public Optional<UserResponse> fetchSingleUser(Long id) {
        return userRepository.findById(id).map(this::mapUserToUserResponse);
    }

    public Boolean updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    getUserFromUserRequest(existingUser, userRequest);
                    userRepository.save(existingUser);
                    return true;
                })
                .orElseGet(() -> false);
    }

    private UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDto = new AddressDTO();
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(addressDto);
        }

        return userResponse;
    }

    private User getUserFromUserRequest(User user, UserRequest userRequest) {
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);
        }
        return user;
    }
}

