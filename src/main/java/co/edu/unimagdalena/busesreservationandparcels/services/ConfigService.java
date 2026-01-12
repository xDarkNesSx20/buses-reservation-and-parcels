package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.ConfigDTOs.*;

import java.util.List;

public interface ConfigService {
    ConfigResponse create(ConfigCreateRequest request);
    ConfigResponse get(String key);
    ConfigResponse update(String key, ConfigUpdateRequest request);
    void delete(String key);

    List<ConfigResponse> getAll();
}
