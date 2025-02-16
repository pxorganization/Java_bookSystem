package org.example.restaurantbooksystem.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.restaurantbooksystem.classes.Reservation;
import org.example.restaurantbooksystem.dtos.ReservationFilterDTO;
import org.example.restaurantbooksystem.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class ReservationController {

    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/filters")
    public List<Reservation> getAllReservationsByFilters(@RequestBody ReservationFilterDTO filters) {
        return reservationService.getReservationsByFilters(filters);
    }

    @PostMapping("/savereservation")
    public void saveReservation(@RequestBody Reservation reservation) {
        reservationService.saveReservation(reservation);
    }

    @PostMapping("/returnable/{id}")
    public Optional<Reservation> reservationById(@PathVariable UUID id) {
        return reservationService.getReservationById(id);
    }

    @DeleteMapping("/cancel/{id}")
    public void deleteReservation(@PathVariable UUID id, HttpServletRequest request) {
        reservationService.deleteReservationById(id, request);
    }

    @PutMapping("/update/{id}")
    public void updateReservation(@PathVariable UUID id, @RequestBody Reservation updatedReservation, HttpServletRequest request) {
        reservationService.updateReservationById(id, updatedReservation, request);
    }
}