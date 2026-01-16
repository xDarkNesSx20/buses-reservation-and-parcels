package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.*;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.FareRuleDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.FareRule;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {StopMapper.class, RouteMapper.class})
public interface FareRuleMapper {

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "basePrice", source = "basePrice")
    @Mapping(target = "dynamicPricing", source = "dynamicPricing")
    FareRule toEntity(FareRuleCreateRequest request);

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "basePrice", source = "basePrice")
    @Mapping(target = "dynamicPricing", source = "dynamicPricing")
    void patch(FareRuleUpdateRequest request, @MappingTarget FareRule entity);

    //Wherever discounts ignore true are, mapping in service
    @Mappings({
        @Mapping(target = "routeId", source = "route.id"),
        @Mapping(target = "fromStopId", source = "fromStop.id"),
        @Mapping(target = "toStopId", source = "toStop.id"),
        @Mapping(target = "discounts", source = "discounts")
    })
    FareRuleBasicResponse toBasicResponse(FareRule entity);

    @Mapping(target = "routeId", source = "route.id")
    @Mapping(target = "discounts", source = "discounts")
    FareRuleResponse toResponse(FareRule entity);

    @Mapping(target = "discounts", source = "discounts")
    FareRuleFullResponse toFullResponse(FareRule entity);

    @Mapping(target = "fromStop", source = "fromStop")
    @Mapping(target = "toStop", source = "toStop")
    FareRuleSummary toSummary(FareRule fareRule);
}
