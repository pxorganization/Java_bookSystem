package org.example.restaurantbooksystem.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.restaurantbooksystem.classes.Reservation;
import org.example.restaurantbooksystem.classes.User;
import org.example.restaurantbooksystem.dtos.ReservationFilterDTO;
import org.example.restaurantbooksystem.dtos.ReservationRequestDTO;
import org.example.restaurantbooksystem.repositories.ReservationDAO;
import org.example.restaurantbooksystem.services.ReservationService;
import org.example.restaurantbooksystem.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final ReservationDAO reservationDAO;


    public ReservationController(ReservationService reservationService, UserService userService, ReservationDAO reservationDAO) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.reservationDAO = reservationDAO;
    }

    @PostMapping("/filters")
    public List<Reservation> getAllReservationsByFilters(@RequestBody ReservationFilterDTO filters) {
        return reservationService.getReservationsByFilters(filters);
    }

    @PostMapping("/savereservation")
    public ResponseEntity<?> saveReservation(@RequestBody Map<String, Object> payload) {

        UUID userId = null;
        if (payload.containsKey("userId") && payload.get("userId") != null){
            userId = UUID.fromString((String) payload.get("userId"));
        }

        Integer tableNumber = (Integer) payload.get("tableNumber");
        Integer people = (Integer) payload.get("people");
        String surname = (String) payload.get("surname");
        String name = (String) payload.get("name");
        String phone = (String) payload.get("phone");
        String email = (String) payload.get("email");
        String notes = (String) payload.get("notes");
        LocalDate date = LocalDate.parse((String) payload.get("date"));
        String time = (String) payload.get("time");
        Boolean createAccount = (Boolean) payload.get("createAccount");

        if (createAccount && userId == null) {
            LocalDate birthDate = LocalDate.parse((String) payload.get("birthDate"));
            User user = new User(name, email, surname + "2025", birthDate);
            User createdUser = userService.registerUser(user);

            Reservation reservation = new Reservation(tableNumber, createdUser.getId(), people, surname, name, phone, createdUser.getEmail(), notes, date, time);
            reservationService.saveReservation(reservation);
            System.out.println();
            return ResponseEntity.ok("createdUser with email: " + createdUser.getEmail() + " and password: " + surname+"2025");
        }else{
            Reservation reservation = new Reservation(tableNumber, userId, people, surname, name, phone, email, notes, date, time);
            reservationService.saveReservation(reservation);
            return ResponseEntity.ok("Reservation saved successfully");
        }
    }

    @PostMapping("/tables")
    public List<Integer> availableTables(@RequestBody ReservationRequestDTO request) {
        return reservationDAO.findReservedTables(request.getPeople(), request.getTime(), request.getDate());
    }

    @PostMapping("/returnable/{id}")
    public Optional<Reservation> reservationById(@PathVariable UUID id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping("/checkin")
    public List<Reservation> reservationCheckIn() {
        return reservationService.getCompletedReservations();
    }

    @PostMapping("/availabletables")
    public List<Integer> availableTablesToday() {
        return reservationService.getTodaysTables();
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