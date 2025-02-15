package org.example.restaurantbooksystem.controllers;

import org.example.restaurantbooksystem.classes.Reservation;
import org.example.restaurantbooksystem.dtos.ReservationFilterDTO;
import org.example.restaurantbooksystem.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/all")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations(); // Return all reservations
    }

    @PostMapping("/allfilters")
    public List<Reservation> getAllReservationsByFilters(@RequestBody ReservationFilterDTO filters) {
        return reservationService.getReservationsByFilters(filters);
    }
}
