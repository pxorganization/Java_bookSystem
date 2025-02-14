package org.example.restaurantbooksystem.controllers;

import org.example.restaurantbooksystem.classes.Reservation;
import org.example.restaurantbooksystem.repositories.ReservationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/all")
    public List<Reservation> getFilteredReservations(
            @RequestParam(required = false) String tableNumber,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String time) {
        return reservationRepository.findFilteredReservations(tableNumber, name, email, phone, date, time);
    }
}
