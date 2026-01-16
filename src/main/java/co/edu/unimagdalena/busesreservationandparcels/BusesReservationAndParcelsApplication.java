package co.edu.unimagdalena.busesreservationandparcels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class BusesReservationAndParcelsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusesReservationAndParcelsApplication.class, args);
    }

}
