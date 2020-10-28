package com.ecasanes.learningspring.service;

import com.ecasanes.learningspring.business.domain.RoomReservation;
import com.ecasanes.learningspring.data.entity.Guest;
import com.ecasanes.learningspring.data.entity.Reservation;
import com.ecasanes.learningspring.data.entity.Room;
import com.ecasanes.learningspring.data.repository.GuestRepository;
import com.ecasanes.learningspring.data.repository.ReservationRepository;
import com.ecasanes.learningspring.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
// a stereotype of @Component which allows component scanning to occur
// @Service - can build aspects against this that won't apply to other classes within my stack
// we can also just do @Component
public class ReservationService {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired // not needed by default
    // benefits: IDE is smart
    // very clear which constructor is called at a given time
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date){
        // Service Object
        // Leverages Spring than Java
            // - there must be other ways

        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomName(room.getRoomName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getRoomId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getGuestId());
        });

        List<RoomReservation> roomReservations = new ArrayList<>();
        for(Long id: roomReservationMap.keySet()){
            roomReservations.add(roomReservationMap.get(id));
        }

        return roomReservations;

    }

}
