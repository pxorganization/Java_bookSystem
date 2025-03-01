package org.example.restaurantbooksystem.services;

import jakarta.servlet.http.HttpServletRequest;
import org.example.restaurantbooksystem.classes.Reservation;
import org.example.restaurantbooksystem.dtos.ReservationFilterDTO;
import org.example.restaurantbooksystem.repositories.ReservationDAO;
import org.example.restaurantbooksystem.repositories.ReservationRepository;
import org.example.restaurantbooksystem.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationDAO reservationDAO;
    private final JwtUtil jwtUtil;

    public ReservationService(ReservationRepository reservationRepository, ReservationDAO reservationDAO, JwtUtil jwtUtil) {
        this.reservationRepository = reservationRepository;
        this.reservationDAO = reservationDAO;
        this.jwtUtil = jwtUtil;
    }

    public List<Reservation> getReservationsByFilters(ReservationFilterDTO filters) {
        if (filters == null || filters.isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            //LocalTime currentTime = LocalTime.now();
            //String formatedTime = DateTimeFormatter.ofPattern("HH:mm").format(currentTime);

            //return reservationRepository.findAllByDateAfterAndTimeAfter(currentDate,formatedTime); // Fetch all reservations
            return reservationRepository.findAllByDateAfterOrderByDateAsc(currentDate); // Fetch all reservations
        } else {
            return reservationDAO.findReservationsByFilters(filters);
        }
    }

    public void saveReservation(Reservation reservation) {
        System.out.println(reservation);
        reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservationById(UUID id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getCompletedReservations() {
        LocalDate currentDate = LocalDate.now();
        return reservationRepository.findAllByIscompletedAndDateEquals(true,currentDate);
    }

    public List<Integer> getTodaysTables() {
        LocalDate currentDate = LocalDate.now();
        return reservationRepository.findTableNumberByDate(currentDate);
    }

    public void deleteReservationById(UUID id, HttpServletRequest request) {
        if( jwtUtil.isAuthenticated(request)){
             reservationRepository.deleteById(id);
        }
    }

    public void updateReservationById(UUID id, Reservation updatedReservation, HttpServletRequest request) {
        if( jwtUtil.isAuthenticated(request)){
            reservationRepository.save(updatedReservation);
        }
    }
}
