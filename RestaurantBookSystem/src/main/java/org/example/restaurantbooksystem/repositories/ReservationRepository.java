package org.example.restaurantbooksystem.repositories;

import org.example.restaurantbooksystem.classes.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findAllByDateAfterAndTimeAfterOrderByDateAsc(LocalDate dateAfter, String timeAfter);

    List<Reservation> findAllByIscompletedAndDateEquals(Boolean iscompleted, LocalDate dateAfter);

    @Query(value = """
    SELECT table_number 
    FROM reservations 
    WHERE date = :date 
      AND TO_TIMESTAMP(time, 'HH24:MI') 
          BETWEEN TO_TIMESTAMP(:startTime, 'HH24:MI') 
              AND TO_TIMESTAMP(:endTime, 'HH24:MI')
    """, nativeQuery = true)
    List<Integer> findTableNumberByDateAndTimeRange(
            @Param("date") LocalDate date,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );
}
