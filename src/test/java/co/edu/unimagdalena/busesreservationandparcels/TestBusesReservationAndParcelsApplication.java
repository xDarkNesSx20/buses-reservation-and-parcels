package co.edu.unimagdalena.busesreservationandparcels;

import org.springframework.boot.SpringApplication;

public class TestBusesReservationAndParcelsApplication {

    public static void main(String[] args) {
        SpringApplication.from(BusesReservationAndParcelsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
