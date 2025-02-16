package org.example.restaurantbooksystem.services;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
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
import java.util.Arrays;
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

            return reservationRepository.findAllByDateAfterAndTimeAfter(currentDate,formatedTime); // Fetch all reservations
        } else {
            return reservationDAO.findReservationsByFilters(filters);
        }
    }

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservationById(UUID id) {
        return reservationRepository.findById(id);
    }

    public void deleteReservationById(UUID id, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        Optional<Cookie> jwtCookie = Arrays.stream(cookies)
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .findFirst();

        if (jwtCookie.isPresent()) {
            String encryptedToken = jwtCookie.get().getValue();
            Claims isValid = jwtUtil.validateToken(encryptedToken);

            if (isValid != null) {
                reservationRepository.deleteById(id);
            }
        }
    }

    public void updateReservationById(UUID id, Reservation updatedReservation, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        Optional<Cookie> jwtCookie = Arrays.stream(cookies)
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .findFirst();

        if (jwtCookie.isPresent()) {
            String encryptedToken = jwtCookie.get().getValue();
            Claims isValid = jwtUtil.validateToken(encryptedToken);

            if (isValid != null) {
                reservationRepository.save(updatedReservation);
            }
        }
    }
}
