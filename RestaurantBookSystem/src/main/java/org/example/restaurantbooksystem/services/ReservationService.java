package org.example.restaurantbooksystem.services;

import jakarta.servlet.http.HttpServletRequest;
import org.example.restaurantbooksystem.classes.Reservation;
import org.example.restaurantbooksystem.dtos.ReservationFilterDTO;
import org.example.restaurantbooksystem.repositories.ReservationDAO;
import org.example.restaurantbooksystem.repositories.ReservationRepository;
import org.example.restaurantbooksystem.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            LocalTime currentTime = LocalTime.now();
            String formatedTime = DateTimeFormatter.ofPattern("HH:mm").format(currentTime);

            return reservationRepository.findAllByDateAfterAndTimeAfterOrderByDateAsc(currentDate,formatedTime);
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
        LocalTime currentTime = LocalTime.now();

        // Calculate the start and end times for the ±2-hour window
        LocalTime startTime = currentTime.minusHours(2);
        LocalTime endTime = currentTime.plusHours(2);

        // Format the times as strings
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = startTime.format(timeFormatter);
        String formattedEndTime = endTime.format(timeFormatter);

        // Handle the case where the time range spans midnight
        if (startTime.isAfter(endTime)) {
            // Fetch records for the current day (from startTime to 23:59)
            List<Integer> tables1 = reservationRepository.findTableNumberByDateAndTimeRange(
                    currentDate, formattedStartTime, "23:59"
            );

            // Fetch records for the next day (from 00:00 to endTime)
            LocalDate nextDate = currentDate.plusDays(1);
            List<Integer> tables2 = reservationRepository.findTableNumberByDateAndTimeRange(
                    nextDate, "00:00", formattedEndTime
            );

            // Combine the results
            List<Integer> allTables = new ArrayList<>(tables1);
            allTables.addAll(tables2);
            return allTables;
        } else {
            // Fetch records for the current day
            return reservationRepository.findTableNumberByDateAndTimeRange(
                    currentDate, formattedStartTime, formattedEndTime
            );
        }
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
