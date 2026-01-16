package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.DiscountDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.DiscountType;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.DiscountRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.FareRuleRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.DiscountService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.DiscountMapper;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.FareRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepo;
    private final DiscountMapper mapper;
    private final FareRuleRepository fareRuleRepo;
    private final FareRuleMapper fareRuleMapper;

    @Override
    @Transactional
    public DiscountFullResponse create(Long fareRuleId, DiscountCreateRequest request) {
        var fareRule = fareRuleRepo.findById(fareRuleId).orElseThrow(
                () -> new NotFoundException("FareRule %d not found.".formatted(fareRuleId))
        );
        var discount = mapper.toEntity(request);
        discount.setFareRule(fareRule);
        var saved = discountRepo.save(discount);

        return new DiscountFullResponse(saved.getId(), fareRuleMapper.toSummary(fareRule),
                saved.getType(), saved.getValue());
    }

    @Override
    @Transactional
    public List<DiscountResponse> createAll(Long fareRuleId, Set<DiscountCreateRequest> requests) {
        var fareRule = fareRuleRepo.findById(fareRuleId).orElseThrow(
                () -> new NotFoundException("FareRule %d not found.".formatted(fareRuleId))
        );
        var discounts = requests.stream().map(mapper::toEntity).toList();
        discounts.forEach(d -> d.setFareRule(fareRule));

        var saved = discountRepo.saveAll(discounts);

        return saved.stream().map(mapper::toResponse).toList();
    }

    @Override
    public DiscountResponse get(Long id) {
        var discount = discountRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Discount %d not found.".formatted(id))
        );
        return mapper.toResponse(discount);
    }

    @Override
    @Transactional
    public DiscountResponse update(Long id, DiscountUpdateRequest request) {
        var discount = discountRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Discount %d not found.".formatted(id))
        );
        mapper.patch(request, discount);
        return mapper.toResponse(discountRepo.save(discount));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        discountRepo.deleteById(id);
    }

    @Override
    public DiscountFullResponse getWithDetails(Long id) {
        var discount = discountRepo.findByIdWithDetails(id).orElseThrow(
                () -> new NotFoundException("Discount %d not found.".formatted(id))
        );
        return new DiscountFullResponse(discount.getId(), fareRuleMapper.toSummary(discount.getFareRule()),
                discount.getType(), discount.getValue());
    }

    @Override
    public List<DiscountFullResponse> getByTypeWithDetails(DiscountType type) {
        return discountRepo.findByTypeWithDetails(type).stream()
                .map(d -> new DiscountFullResponse(
                        d.getId(), fareRuleMapper.toSummary(d.getFareRule()),
                        d.getType(), d.getValue()
                ))
                .toList();
    }

    @Override
    public List<DiscountResponse> getByType(DiscountType type) {
        return discountRepo.findByType(type).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<DiscountResponse> getByFareRule(Long fareRuleId) {
        return discountRepo.findByFareRule_Id(fareRuleId).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
