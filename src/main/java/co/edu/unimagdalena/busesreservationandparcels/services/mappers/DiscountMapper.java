package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.DiscountDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Discount;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fareRule", ignore = true)
    Discount toEntity(DiscountCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
    @Mapping(target = "type", source = "type")
    @Mapping(target = "value", source = "value")
    void patch(DiscountUpdateRequest request, @MappingTarget Discount entity);

    @Mapping(target = "fareRuleId", source = "fareRule.id")
    DiscountResponse toResponse(Discount entity);

    Set<DiscountResponse> toSetResponse(Set<Discount> discounts);
}
