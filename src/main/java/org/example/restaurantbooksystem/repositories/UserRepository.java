package org.example.restaurantbooksystem.repositories;

import org.example.restaurantbooksystem.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUsername(String username);
    User findByEmail(String email);
    User findByBirthyear(LocalDate birthyear);
    List<User> findAllByRole(String role);

}
