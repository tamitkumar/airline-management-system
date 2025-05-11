package airline.management.system.booking;

import airline.management.system.entity.BookingEntity;
import airline.management.system.entity.FlightEntity;
import airline.management.system.entity.PassengerEntity;
import airline.management.system.entity.SeatEntity;
import airline.management.system.exception.AirlineException;
import airline.management.system.repository.BookingRepository;
import airline.management.system.service.ReservationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookingManager {

    private final Object lock = new Object();
    private final BookingRepository bookingRepository;
    private final ReservationService reservationService;
    public BookingManager(BookingRepository bookingRepository, ReservationService reservationService) {
        this.bookingRepository = bookingRepository;
        this.reservationService = reservationService;
    }

    public BookingEntity createBooking(FlightEntity flight, PassengerEntity passenger, SeatEntity seat, double price) {
        String bookingNumber = UUID.randomUUID().toString();
        BookingEntity bookingEntity = BookingEntity.builder().id(bookingNumber).flight(flight).passenger(passenger).seat(seat).status(BookingStatus.CONFIRMED).price(price).build();
        return bookingRepository.save(bookingEntity);
    }

    public void cancelBooking(String bookingNumber) {
        synchronized (lock) {
            BookingEntity booking = bookingRepository.findById(bookingNumber).orElseThrow(() -> new AirlineException("ERR-502", "Booking not found with Booking Number: " + bookingNumber));
            if (booking != null) {
                reservationService.releaseSeat(booking.getSeat().getSeatNumber());
                booking.cancel();

            }
        }
    }
}
