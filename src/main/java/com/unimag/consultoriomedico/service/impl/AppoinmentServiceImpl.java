package com.unimag.consultoriomedico.service.impl;

import com.unimag.consultoriomedico.dto.AppointmentDTO;
import com.unimag.consultoriomedico.dto.DoctorDTO;
import com.unimag.consultoriomedico.exception.*;
import com.unimag.consultoriomedico.mapper.AppointmentMapper;
import com.unimag.consultoriomedico.model.Appointment;
import com.unimag.consultoriomedico.model.Status;
import com.unimag.consultoriomedico.repository.AppointmentRepository;
import com.unimag.consultoriomedico.repository.ConsultRoomRepository;
import com.unimag.consultoriomedico.repository.DoctorRepository;
import com.unimag.consultoriomedico.service.AppoinmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppoinmentServiceImpl implements AppoinmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;
    private final ConsultRoomRepository consultRoomRepository;

    @Override
    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream().map(appointmentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO findById(Long id) {
        if(!appointmentRepository.existsById(id)){
            throw new ResourceNotFoundException("Appointment not found "+id);
        }
        return appointmentMapper.toDTO(appointmentRepository.findById(id).get());
    }

    @Override
    public AppointmentDTO create(AppointmentDTO appointmentDto) {
        Long doctorId= appointmentDto.getDoctorId();
        Long consultRoomId=appointmentDto.getConsultRoomId();
        LocalDateTime startTime = appointmentDto.getStartTime();
        LocalDateTime endTime = appointmentDto.getEndTime();

        if(isInThePast(appointmentDto.getStartTime())){
            throw new InvalidAppointmentTimeException("Appointment start time cannot be in the past");
        }

        if(consultRoomRepository.consultRoomHasAppointment(consultRoomId,startTime,endTime)){
            throw new TimeConflictException("ConsultRoom already has an appointment");
        }

        if(doctorRepository.hasAppointmentsInTimeRange(doctorId,startTime,endTime)){
            throw new TimeConflictException("Doctor already has an appointment");
        }

        if(!isValidAppointmentDuration(startTime,endTime)){
            throw new InvalidAppointmentDurationException("Appointment duration must be between 15 and 60 minutes");
        }

        if (!doctorRepository.isWithinDoctorSchedule(doctorId, startTime, endTime)) {
            throw new InvalidAppointmentTimeException("Appointment is outside the doctor's working hours");
        }

        if(checkPointStatus(appointmentDto.getId())!=Status.SCHEDULED){
            throw new InvalidAppointmentStatusException("Appointment is not scheduled");
        }

        return appointmentMapper.toDTO(appointmentRepository.save(appointmentMapper.toEntity(appointmentDto)));

    }

    @Override
    public AppointmentDTO update(Long id, AppointmentDTO appointmentDto) {
        if(!appointmentRepository.existsById(id)){
            throw new ResourceNotFoundException("Appointment not found "+id);
        }
        Appointment appointment = appointmentRepository.findById(id).get();
        Long appointmentId = appointmentDto.getId();
        Long doctorId= appointmentDto.getDoctorId();
        Long consultRoomId=appointmentDto.getConsultRoomId();
        LocalDateTime startTime = appointmentDto.getStartTime();
        LocalDateTime endTime = appointmentDto.getEndTime();
        Status status = appointmentDto.getStatus();

        if(isInThePast(appointmentDto.getStartTime())){
            throw new InvalidAppointmentTimeException("Appointment start time cannot be in the past");
        }

        if(!isValidAppointmentDuration(startTime,endTime)){
            throw new InvalidAppointmentDurationException("Appointment duration must be between 15 and 60 minutes");
        }

        if(consultRoomRepository.consultRoomHasAppointment(consultRoomId,startTime,endTime)){
            throw new TimeConflictException("ConsultRoom already has an appointment");
        }

        if(doctorRepository.hasAppointmentsInTimeRange(doctorId,startTime,endTime)){
            throw new TimeConflictException("Doctor already has an appointment");
        }

        if(checkPointStatus(appointmentId)!=Status.SCHEDULED){
            throw new InvalidAppointmentStatusException("Only scheduled appointments can be updated");
        }

        if (!doctorRepository.isWithinDoctorSchedule(doctorId, startTime, endTime)) {
            throw new InvalidAppointmentTimeException("Appointment is outside the doctor's working hours");
        }

        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setStatus(status);
        return appointmentMapper.toDTO(appointmentRepository.save(appointment));


    }

    @Override
    public void cancel(Long id) {
        if(!appointmentRepository.existsById(id)){
            throw new ResourceNotFoundException("Appointment not found "+id);
        }
        Appointment appointment = appointmentRepository.findById(id).get();
        if(checkPointStatus(id)!=Status.SCHEDULED){
            throw new InvalidAppointmentStatusException("Appointment status already was finished");
        }
        appointment.setStatus(Status.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Override
    public boolean isValidAppointmentDuration(LocalDateTime startTime, LocalDateTime endTime) {
        long durationMinutes = Duration.between(startTime, endTime).toMinutes();
        if(durationMinutes >= 15 && durationMinutes <= 60){
           return true;
        }
        return false;
    }

    @Override
    public boolean isInThePast(LocalDateTime startTime) {
        return startTime.isBefore(LocalDateTime.now());
    }

    @Override
    public Status checkPointStatus(Long id) {
        return appointmentRepository.findById(id).map(Appointment::getStatus).orElseThrow(() -> new ResourceNotFoundException("Appointment not found "+id));
    }

    @Override
    public void delete(Long id) {
        if(!appointmentRepository.existsById(id)){
            throw new ResourceNotFoundException("Appointment not found "+id);
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<AppointmentDTO> findAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        if(!doctorRepository.existsById(doctorId)){
            throw new ResourceNotFoundException("Doctor not found "+doctorId);
        }
        return appointmentRepository.findAppointmentsByDoctorAndDate(doctorId,date).stream().map(appointmentMapper::toDTO).collect(Collectors.toList());
    }
}
