package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.BaggageDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.BaggageRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.TicketRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.BaggageService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.BaggageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaggageServiceImpl implements BaggageService {
    private final BaggageRepository baggageRepo;
    private final BaggageMapper mapper;
    private final TicketRepository ticketRepo;

    @Override
    @Transactional
    public BaggageResponse create(Long ticketId, BaggageCreateRequest request) {
        var ticket = ticketRepo.findById(ticketId).orElseThrow(
                () -> new NotFoundException("Ticket %d not found.".formatted(ticketId))
        );
        var baggage = mapper.toEntity(request);
        baggage.setTagCode(generateTagCode());
        baggage.setTicket(ticket);
        return mapper.toResponse(baggageRepo.save(baggage));
    }

    private String generateTagCode() {
        var uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return "BAG-" + uuid.substring(0, 8);
    }

    @Override
    @Transactional
    public List<BaggageResponse> createAll(Long ticketId, Set<BaggageCreateRequest> requests) {
        var ticket = ticketRepo.findById(ticketId).orElseThrow(
                () -> new NotFoundException("Ticket %d not found.".formatted(ticketId))
        );
        var baggages = requests.stream().map(mapper::toEntity).toList();
        baggages.forEach(b -> {
            b.setTagCode(generateTagCode());
            b.setTicket(ticket);
        });
        return baggageRepo.saveAll(baggages).stream().map(mapper::toResponse).toList();
    }

    @Override
    public BaggageResponse get(Long id) {
        var baggage = baggageRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Baggage %d not found.".formatted(id))
        );
        return mapper.toResponse(baggage);
    }

    @Override
    @Transactional
    public BaggageResponse update(Long id, BaggageUpdateRequest request) {
        var baggage = baggageRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Baggage %d not found.".formatted(id))
        );
        mapper.patch(request, baggage);
        return mapper.toResponse(baggageRepo.save(baggage));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        baggageRepo.deleteById(id);
    }

    @Override
    public List<BaggageResponse> getByTicket(Long ticketId) {
        return baggageRepo.findByTicket_Id(ticketId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public BaggageResponse getByTagCode(String tagCode) {
        var baggage = baggageRepo.findByTagCode(tagCode).orElseThrow(
                () -> new NotFoundException("Baggage %s not found.".formatted(tagCode))
        );
        return mapper.toResponse(baggage);
    }

    @Override
    public Page<BaggageResponse> getByWeightGreaterThan(BigDecimal minWeight, Pageable pageable) {
        return baggageRepo.findByWeightKgGreaterThanEqual(minWeight, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public long countByTrunkNumber(Integer trunkNumber) {
        return baggageRepo.countByTrunkNumber(trunkNumber);
    }
}
