package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.SeatDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SeatType;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.BusRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.SeatRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.SeatService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.SeatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepo;
    private final SeatMapper mapper;
    private final BusRepository busRepo;

    @Override
    @Transactional
    public SeatFullResponse create(Long busId, SeatCreateRequest request) {
        var bus = busRepo.findById(busId).orElseThrow(
                () -> new NotFoundException("Bus with id %d not found.".formatted(busId))
        );
        var seat = mapper.toEntity(request);
        seat.setBus(bus);
        return mapper.toFullResponse(seatRepo.save(seat));
    }

    @Override
    @Transactional
    public List<SeatResponse> createAll(Long busId, Set<SeatCreateRequest> requests) {
        var bus = busRepo.findById(busId).orElseThrow(
                () -> new NotFoundException("Bus with id %d not found.".formatted(busId))
        );
        var seats = requests.stream()
                .map(req -> {
                    var seat = mapper.toEntity(req);
                    seat.setBus(bus);
                    return seat;
                })
                .toList();
        return seatRepo.saveAll(seats).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public SeatResponse get(Long id) {
        var seat = seatRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Seat with id %d not found.".formatted(id))
        );
        return mapper.toResponse(seat);
    }

    @Override
    @Transactional
    public SeatResponse update(Long id, SeatUpdateRequest request) {
        var seat = seatRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Seat with id %d not found.".formatted(id))
        );
        mapper.patch(request, seat);
        return mapper.toResponse(seatRepo.save(seat));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        seatRepo.deleteById(id);
    }

    @Override
    public List<SeatResponse> getByBus(Long busId) {
        if (!busRepo.existsById(busId))
            throw new NotFoundException("Bus with id %d not found.".formatted(busId));
        return seatRepo.findByBus_Id(busId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<SeatResponse> getByBusAndType(Long busId, SeatType type) {
        if (!busRepo.existsById(busId))
            throw new NotFoundException("Bus with id %d not found.".formatted(busId));

        return seatRepo.findByBus_IdAndType(busId, type).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public SeatResponse getByBusAndNumber(Long busId, String number) {
        if (!busRepo.existsById(busId))
            throw new NotFoundException("Bus with id %d not found.".formatted(busId));

        var seat = seatRepo.findByBus_IdAndNumber(busId, number).orElseThrow(
                () -> new NotFoundException("Seat with number %s not found in bus with id %d.".formatted(number, busId))
        );
        return mapper.toResponse(seat);
    }

    @Override
    public SeatFullResponse getWithDetails(Long id) {
        var seat = seatRepo.findByIdWithBus(id).orElseThrow(
                () -> new NotFoundException("Seat with id %d not found.".formatted(id))
        );
        return mapper.toFullResponse(seat);
    }
}
