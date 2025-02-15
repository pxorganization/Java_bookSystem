package org.example.restaurantbooksystem.services;

import org.example.restaurantbooksystem.classes.Reservation;
import org.example.restaurantbooksystem.dtos.ReservationFilterDTO;
import org.example.restaurantbooksystem.repositories.ReservationDAO;
import org.example.restaurantbooksystem.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationRepository reservationRepository, ReservationDAO reservationDAO) {
        this.reservationRepository = reservationRepository;
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll(); // Fetch all reservations
    }

    public List<Reservation> getReservationsByFilters(ReservationFilterDTO filters) {
        if (filters == null || filters.isEmpty()) {
            return getAllReservations();
        } else {
            return reservationDAO.findReservationsByFilters(filters);
        }
    }
}
