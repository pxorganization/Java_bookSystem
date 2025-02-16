package org.example.restaurantbooksystem.repositories;

import org.example.restaurantbooksystem.classes.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findAllByDateAfterAndTimeAfter(LocalDate dateAfter, String timeAfter);
}