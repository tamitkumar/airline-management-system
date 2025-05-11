package airline.management.system.service;

import airline.management.system.entity.SeatEntity;
import airline.management.system.exception.AirlineException;
import airline.management.system.repository.SeatRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final SeatRepository seatRepository;

    public ReservationService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public synchronized void reserveSeat(String seatNo) {
        SeatEntity seat = seatRepository.findById(seatNo).orElseThrow(() -> new AirlineException("ERR-502", "Seat not found with id: " + seatNo));
        if (seat == null) {
            throw new IllegalArgumentException("Invalid seat number...");
        }
        seat.reserve();
        seatRepository.save(seat);
    }

    public synchronized void releaseSeat(String seatNo) {
        SeatEntity seat = seatRepository.findById(seatNo).orElseThrow(() -> new AirlineException("ERR-502", "Seat not found with id: " + seatNo));
        if (seat != null) {
            seat.release();
            seatRepository.save(seat);
        }
    }
}
