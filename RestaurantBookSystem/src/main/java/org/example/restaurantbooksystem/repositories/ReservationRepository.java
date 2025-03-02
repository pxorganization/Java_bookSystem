package org.example.restaurantbooksystem.repositories;

import org.example.restaurantbooksystem.classes.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    //List<Reservation> findAllByDateAfterAndTimeAfterOrderByDateAsc(LocalDate dateAfter, String timeAfter);
    List<Reservation> findAllByDateAfterOrderByDateAsc(LocalDate dateAfter);

    List<Reservation> findAllByIscompletedAndDateEquals(Boolean iscompleted, LocalDate dateAfter);

    @Query("SELECT r.tableNumber FROM Reservation r WHERE r.date = :date")
    List<Integer> findTableNumberByDate(@Param("date") LocalDate date);
}
