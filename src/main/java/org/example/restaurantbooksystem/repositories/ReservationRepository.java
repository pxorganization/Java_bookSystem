package org.example.restaurantbooksystem.repositories;

import org.example.restaurantbooksystem.classes.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("SELECT r FROM Reservation r WHERE " +
            "(:tableNumber IS NULL OR r.tableNumber = :tableNumber) AND " +
            "(:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:email IS NULL OR LOWER(r.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:phone IS NULL OR r.phone LIKE CONCAT('%', :phone, '%')) AND " +
            "(:date IS NULL OR r.date = :date) AND " +
            "(:time IS NULL OR r.time = :time)")
    List<Reservation> findFilteredReservations(
            @Param("tableNumber") String tableNumber,
            @Param("name") String name,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("date") String date,
            @Param("time") String time
    );
}
