package com.ecasanes.learningspring;

import com.ecasanes.learningspring.data.entity.Guest;
import com.ecasanes.learningspring.data.entity.Reservation;
import com.ecasanes.learningspring.data.entity.Room;
import com.ecasanes.learningspring.data.repository.GuestRepository;
import com.ecasanes.learningspring.data.repository.ReservationRepository;
import com.ecasanes.learningspring.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class LearningSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningSpringApplication.class, args);
	}

	// This is not recommended for production
	// only for demo purposes

	@RestController
	@RequestMapping("/rooms")
	public class RoomController{
		@Autowired
		private RoomRepository roomRepository;

		@GetMapping
		public Iterable<Room> getRooms(){
			return this.roomRepository.findAll();
		}
	}

	@RestController
	@RequestMapping("/guests")
	public class GuestController{
		@Autowired
		private GuestRepository guestRepository;

		@GetMapping
		public Iterable<Guest> getGuests(){
			return this.guestRepository.findAll();
		}
	}

	@RestController
	@RequestMapping("/reservations")
	public class ReservationController{
		@Autowired
		private ReservationRepository reservationRepository;

		@GetMapping
		public Iterable<Reservation> getReservations(){
			return this.reservationRepository.findAll();
		}
	}


	// CLEAN BUILD - gradle clean
	// BUILD JAR - gradlew build

	// Embedded Database
	// data.sql
	// schema.sql

	// OLD
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
	// compile group: 'org.springframework.boot', name: 'spring-boot-starter-data-jpa', version: '2.3.4.RELEASE'
	// we don't need to specify version as jpa already specified it for us???

	// NEW and WORKING
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
	//implementation group: 'org.springframework.boot', name: 'spring-boot-starter-data-jpa', version: '2.3.4.RELEASE'
	// https://mvnrepository.com/artifact/com.h2database/h2
	//runtimeOnly group: 'com.h2database', name: 'h2'

	// BY USING SPRING DATA - fewer lines of boilerplate code
	// FOCUS ON BUSINESS DOMAIN


	// RUNNING REMOTE DATABASE in bin/start_postgres.sh



}
