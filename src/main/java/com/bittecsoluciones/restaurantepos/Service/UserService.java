package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.Entity.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> findByUsername(String username);
}
