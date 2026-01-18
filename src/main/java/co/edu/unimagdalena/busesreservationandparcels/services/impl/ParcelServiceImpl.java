package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.ParcelDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.ParcelStatus;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.AppUserRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.ParcelRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.StopRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.ParcelService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.ParcelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParcelServiceImpl implements ParcelService {
    private final ParcelRepository parcelRepo;
    private final ParcelMapper mapper;
    private final StopRepository stopRepo;
    private final AppUserRepository  appUserRepo;

    //TODO: I need to add something in Config as default value for Parcels
    @Override
    public ParcelFullResponse create(ParcelCreateRequest request) {
        var fromStop = stopRepo.findById(request.fromStopId()).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(request.fromStopId()))
        );
        var toStop = stopRepo.findById(request.toStopId()).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(request.toStopId()))
        );
        if (!fromStop.getRoute().getId().equals(toStop.getRoute().getId()))
            throw new IllegalArgumentException("FromStop and ToStop have to be in the same route.");

        if (toStop.getStopOrder() <= fromStop.getStopOrder())
            throw new IllegalArgumentException("ToStop order must be greater than FromStop order.");

        var sender = appUserRepo.findById(request.senderId()).orElseThrow(
                () -> new NotFoundException("User with id %d not found.".formatted(request.senderId()))
        );
        var receiver = appUserRepo.findById(request.receiverId()).orElseThrow(
                ()  -> new NotFoundException("User with id %d not found.".formatted(request.receiverId()))
        );
        if (!receiver.getActive())
            throw new IllegalArgumentException("User with id %d is not active.".formatted(request.receiverId()));

        var parcel = mapper.toEntity(request);
        parcel.setFromStop(fromStop);
        parcel.setToStop(toStop);
        parcel.setSender(sender);
        parcel.setReceiver(receiver);
        parcel.setCode(generateCode());

        return mapper.toFullResponse(parcelRepo.save(parcel));
    }

    private String generateCode() {
        return "PAR-" + UUID.randomUUID().toString().substring(0, 16).toUpperCase();
    }

    @Override
    public ParcelResponse get(Long id) {
        var parcel = parcelRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Parcel with id %d not found.".formatted(id))
        );
        return mapper.toResponse(parcel);
    }

    @Override
    public ParcelResponse update(Long id, ParcelUpdateRequest request) {
        var parcel = parcelRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Parcel with id %d not found.".formatted(id))
        );
        mapper.patch(request, parcel);

        if (request.status().equals(ParcelStatus.READY_FOR_PICKUP))
            parcel.setDeliveryOTP(generateDeliveryOTP());
            //Here it should send a notif to the receiver

        return mapper.toResponse(parcelRepo.save(parcel));
    }

    private String generateDeliveryOTP(){
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public void delete(Long id) {
        parcelRepo.deleteById(id);
    }

    @Override
    public ParcelFullResponse getWithDetails(Long id) {
        var parcel = parcelRepo.findByIdWithDetails(id).orElseThrow(
                () -> new NotFoundException("Parcel with id %d not found.")
        );
        return mapper.toFullResponse(parcel);
    }

    @Override
    public ParcelFullResponse getByDeliveryOTP(String deliveryOTP) {
        var parcel = parcelRepo.findByDeliveryOTP(deliveryOTP).orElseThrow(
                () -> new NotFoundException("Parcel with OTP %s not found.".formatted(deliveryOTP))
        );
        return mapper.toFullResponse(parcel);
    }

    @Override
    public ParcelFullResponse getByCode(String code) {
        var parcel = parcelRepo.findByCode(code).orElseThrow(
                () -> new NotFoundException("Parcel with code %s not found.".formatted(code))
        );
        return mapper.toFullResponse(parcel);
    }

    @Override
    public List<ParcelResponse> getByFromStop(Long fromStopId) {
        if (!stopRepo.existsById(fromStopId))
            throw new NotFoundException("Stop with id %d not found.".formatted(fromStopId));
        return parcelRepo.findByFromStop_Id(fromStopId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ParcelResponse> getByToStop(Long toStopId) {
        if (!stopRepo.existsById(toStopId))
            throw new NotFoundException("Stop with id %d not found.".formatted(toStopId));
        return parcelRepo.findByToStop_Id(toStopId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ParcelResponse> getByStops(Long fromStopId, Long toStopId) {
        if (!stopRepo.existsById(fromStopId))
            throw new NotFoundException("Stop with id %d not found.".formatted(fromStopId));
        if (!stopRepo.existsById(toStopId))
            throw new NotFoundException("Stop with id %d not found.".formatted(toStopId));

        return parcelRepo.findByFromStop_IdAndToStop_Id(fromStopId, toStopId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ParcelResponse> getByStopsAndStatus(Long fromStopId, Long toStopId, ParcelStatus status) {
        if (!stopRepo.existsById(fromStopId))
            throw new NotFoundException("Stop with id %d not found.".formatted(fromStopId));
        if (!stopRepo.existsById(toStopId))
            throw new NotFoundException("Stop with id %d not found.".formatted(toStopId));

        return parcelRepo.findByFromStop_IdAndToStop_IdAndStatus(fromStopId, toStopId, status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ParcelResponse> getByFromStopAndStatus(Long fromStopId, ParcelStatus status) {
        if (!stopRepo.existsById(fromStopId))
            throw new NotFoundException("Stop with id %d not found.".formatted(fromStopId));

        return parcelRepo.findByFromStop_IdAndStatus(fromStopId, status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ParcelResponse> getByToStopAndStatus(Long toStopId, ParcelStatus status) {
        if (!stopRepo.existsById(toStopId))
            throw new NotFoundException("Stop with id %d not found.".formatted(toStopId));

        return parcelRepo.findByToStop_IdAndStatus(toStopId, status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ParcelResponse> getBySender(Long senderId) {
        if (!appUserRepo.existsById(senderId))
            throw new NotFoundException("App user with id %d not found.".formatted(senderId));

        return parcelRepo.findBySender_Id(senderId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ParcelResponse> getByReceiver(Long receiverId) {
        if (!appUserRepo.existsById(receiverId))
            throw new NotFoundException("App user with id %d not found.".formatted(receiverId));

        return parcelRepo.findByReceiver_Id(receiverId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ParcelResponse> getByStatus(ParcelStatus status) {
        return parcelRepo.findByStatus(status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ParcelResponse> getIncoming(Long toStopId) {
        if (!stopRepo.existsById(toStopId))
            throw new NotFoundException("Stop with id %d not found.".formatted(toStopId));

        return parcelRepo.getIncomingParcels(toStopId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ParcelResponse> getWaitingToGetInTransit(Long fromStopId) {
        if (!stopRepo.existsById(fromStopId))
            throw new NotFoundException("Stop with id %d not found.".formatted(fromStopId));

        return parcelRepo.getParcelsToPickUpInFromStop(fromStopId).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
