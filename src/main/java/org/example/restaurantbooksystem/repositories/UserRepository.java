package org.example.restaurantbooksystem.repositories;

import org.example.restaurantbooksystem.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    // Find a user by username
    User findByUsername(String username);

    // Optionally, find a user by email
    User findByEmail(String email);

}
