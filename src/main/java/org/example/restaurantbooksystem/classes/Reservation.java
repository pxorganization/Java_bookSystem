package org.example.restaurantbooksystem.classes;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "table_number")
    private int tableNumber;

    private int people;
    private String surname;
    private String name;
    private String phone;
    private String email;
    private String notes;
    private LocalDate date;
    private String time;

    public Reservation() {}

    public Reservation(int tableNumber, int people, String surname, String name, String phone, String email, String notes, LocalDate date, String time) {
        this.tableNumber = tableNumber;
        this.people = people;
        this.surname = surname;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
        this.date = date;
        this.time = time;
    }

    // Getters / Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {}
    public int getTableNumber() {
        return tableNumber;
    }
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    public int getPeople() {
        return people;
    }
    public void setPeople(int people) {
        this.people = people;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Reservation:" + getId() + ", " + getTableNumber() + ", " + getPeople() + ", " + getSurname() + ", " + getName() + ", " + getPhone() + ", " + getEmail() + ", " + getNotes() + ", " + getDate() + ", " + getTime();
    }
}
