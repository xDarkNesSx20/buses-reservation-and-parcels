package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.AppUser;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.OfflineUser;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "email", expression = "java(getEmail(user))")
    @Mapping(target = "idNumber", expression = "java(getIdNumber(user))")
    UserSummary toSummary(User user);

    default String getEmail(User user) {
        if (user instanceof AppUser au) return au.getEmail();
        return null;
    }

    default String getIdNumber(User user) {
        if (user instanceof OfflineUser ou) return ou.getIdNumber();
        return null;
    }
}
