package org.example.restaurantbooksystem.repositories;

import org.example.restaurantbooksystem.classes.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}