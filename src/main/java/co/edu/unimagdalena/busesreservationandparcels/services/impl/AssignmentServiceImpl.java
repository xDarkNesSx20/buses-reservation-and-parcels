package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.AssignmentDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Assignment;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.AppUserRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.AssignmentRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.TripRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.InactiveUserException;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.AssignmentService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.AssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepo;
    private final AssignmentMapper mapper;
    private final TripRepository tripRepo;
    private final AppUserRepository appUserRepo;

    @Override
    @Transactional
    public AssignmentFullResponse create(Long tripId, AssignmentCreateRequest request) {
        var trip = tripRepo.findById(tripId).orElseThrow(
                () -> new NotFoundException("Trip %d not found.".formatted(tripId))
        );
        var driver = appUserRepo.findById(request.driverId()).orElseThrow(
                () -> new NotFoundException("Driver %d not found.".formatted(request.driverId()))
        );
        if (!driver.getActive())
            throw new InactiveUserException("Driver %d is not active.".formatted(request.driverId()));

        var dispatcher = appUserRepo.findById(request.dispatcherId()).orElseThrow(
                () -> new NotFoundException("Dispatcher %d not found.".formatted(request.dispatcherId()))
        );
        if (!dispatcher.getActive())
            throw new InactiveUserException("Dispatcher %d is not active.".formatted(request.dispatcherId()));

        var assignment = Assignment.builder().trip(trip).driver(driver)
                .dispatcher(dispatcher).build();
        return mapper.toFullResponse(assignmentRepo.save(assignment));
    }

    @Override
    public AssignmentResponse get(Long id) {
        var assignment = assignmentRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Assignment %d not found.".formatted(id))
        );
        return mapper.toResponse(assignment);
    }

    @Override
    @Transactional
    public AssignmentResponse update(Long id, AssignmentUpdateRequest request) {
        var assignment = assignmentRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Assignment %d not found.".formatted(id))
        );
        mapper.patch(request, assignment);

        if (request.driverId() != null) {
            var driver = appUserRepo.findById(request.driverId()).orElseThrow(
                    () -> new NotFoundException("Driver %d not found.".formatted(request.driverId()))
            );
            if (!driver.getActive())
                throw new InactiveUserException("Driver %d is not active.".formatted(request.driverId()));

            assignment.setDriver(driver);
        }
        if (request.dispatcherId() != null) {
            var dispatcher = appUserRepo.findById(request.dispatcherId()).orElseThrow(
                    () -> new NotFoundException("Dispatcher %d not found.".formatted(request.dispatcherId()))
            );
            if (!dispatcher.getActive())
                throw new InactiveUserException("Dispatcher %d is not active.".formatted(request.dispatcherId()));

            assignment.setDispatcher(dispatcher);
        }
        return mapper.toResponse(assignmentRepo.save(assignment));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        assignmentRepo.deleteById(id);
    }

    @Override
    public AssignmentResponse getByTrip(Long tripId) {
        var assignment = assignmentRepo.findById(tripId).orElseThrow(
                () -> new NotFoundException("Assignment for trip %d not found.".formatted(tripId))
        );
        return mapper.toResponse(assignment);
    }

    @Override
    public List<AssignmentResponse> getByCheckList(Boolean checkList) {
        return assignmentRepo.findByCheckListOk(checkList).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<AssignmentResponse> getByDriver(Long driverId) {
        return assignmentRepo.findByDriver_Id(driverId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<AssignmentResponse> getByDispatcher(Long dispatcherId) {
        return assignmentRepo.findByDispatcher_Id(dispatcherId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public Page<AssignmentResponse> getByAssignedAt(OffsetDateTime from, OffsetDateTime to,
                                                    Pageable pageable) {
        if (to.isAfter(from))
            throw new IllegalArgumentException("'From' date must be after 'to' date.");

        var assignments = assignmentRepo.findByAssignedAtBetween(from, to, pageable);
        return assignments.map(mapper::toResponse);
    }

    @Override
    public List<AssignmentFullResponse> getByDriverWithDetails(Long driverId) {
        return assignmentRepo.findByDriver_IdWithDetails(driverId).stream()
                .map(mapper::toFullResponse)
                .toList();
    }

    @Override
    public List<AssignmentFullResponse> getByDispatcherWithDetails(Long dispatcherId) {
        return assignmentRepo.findByDispatcher_IdWithDetails(dispatcherId).stream()
                .map(mapper::toFullResponse)
                .toList();
    }
}
