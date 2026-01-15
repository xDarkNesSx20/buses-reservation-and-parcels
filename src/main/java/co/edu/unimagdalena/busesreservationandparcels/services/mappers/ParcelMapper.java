package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.ParcelDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Parcel;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UserMapper.class, StopMapper.class})
public interface ParcelMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "price", source = "price")
    Parcel toEntity(ParcelCreateRequest request);

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "price", source = "price")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deliveryOTP", source = "deliveryOTP")
    void patch(ParcelUpdateRequest request, @MappingTarget Parcel entity);

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "receiverId", source = "receiver.id")
    ParcelResponse toResponse(Parcel entity);

    ParcelFullResponse toFullResponse(Parcel entity);
}
