package org.example.restaurantbooksystem.repositories;

import org.example.restaurantbooksystem.classes.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAllByDateAfterAndTimeAfter(LocalDate dateAfter, String timeAfter);

}