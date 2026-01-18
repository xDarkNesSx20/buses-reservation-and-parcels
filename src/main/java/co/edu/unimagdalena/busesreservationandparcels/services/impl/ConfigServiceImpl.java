package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.ConfigDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.ConfigRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.AlreadyExistsException;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.ConfigService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.ConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConfigServiceImpl implements ConfigService {
    private final ConfigRepository configRepo;
    private final ConfigMapper mapper;

    //TODO: I could add caching to this service, 'cause configs are not changed that often

    @Override
    public ConfigResponse create(ConfigCreateRequest request) {
        if (configRepo.existsByKeyIgnoreCase(request.key()))
            throw new AlreadyExistsException("Config with name %s already exists.".formatted(request.key()));
        var config = mapper.toEntity(request);
        return mapper.toResponse(configRepo.save(config));
    }

    @Override
    @Transactional(readOnly = true)
    public ConfigResponse get(String key) {
        var config = configRepo.findByKeyIgnoreCase(key).orElseThrow(
                () -> new NotFoundException("Config with name %s not found.".formatted(key))
        );
        return mapper.toResponse(config);
    }

    @Override
    public ConfigResponse update(String key, ConfigUpdateRequest request) {
        var config = configRepo.findByKeyIgnoreCase(key).orElseThrow(
                () -> new NotFoundException("Config with name %s not found.".formatted(key))
        );
        mapper.patch(request, config);
        return mapper.toResponse(configRepo.save(config));
    }

    @Override
    public void delete(String key) {
        configRepo.deleteByKeyIgnoreCase(key);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfigResponse> getAll() {
        return configRepo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }
}
