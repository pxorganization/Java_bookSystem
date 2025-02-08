package org.example.restaurantbooksystem.repositories;

import org.example.restaurantbooksystem.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    // Find a user by username
    User findByUsername(String username);

    // Find a user by birth year
    User findByBirthyear(LocalDate birthyear);

    // Find all users with role
    List<User> findAllByRole(String role);

    // Find a user by email
    User findByEmail(String email);

}
