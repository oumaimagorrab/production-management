package com.production.management.services;

import com.production.management.dtos.MaintenanceDTO;
import com.production.management.entities.Machine;
import com.production.management.entities.Maintenance;
import com.production.management.entities.Technicien;
import com.production.management.mappers.MaintenanceMapper;
import com.production.management.repository.MachineRepository;
import com.production.management.repository.MaintenanceRepository;
import com.production.management.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepo;

    @Autowired
    private MachineRepository machineRepo;

    @Autowired
    private TechnicienRepository technicienRepo;

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    public Optional<MaintenanceDTO> getMaintenance(Long id) {
        return maintenanceRepo.findById(id).map(maintenanceMapper::toDTO);
    }

    public List<MaintenanceDTO> getAll() {
        return maintenanceRepo.findAll()
                .stream()
                .map(maintenanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getByMachine(Long machineId) {
        return maintenanceRepo.findByMachineId(machineId)
                .stream()
                .map(maintenanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getByTechnicien(Long technicienId) {
        return maintenanceRepo.findByTechnicienId(technicienId)
                .stream()
                .map(maintenanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getByType(Maintenance.TypeMaintenance type) {
        return maintenanceRepo.findByType(type)
                .stream()
                .map(maintenanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getPlanifiees() {
        return maintenanceRepo.findByDateGreaterThanEqual(LocalDate.now())
                .stream()
                .map(maintenanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getEntreDates(LocalDate debut, LocalDate fin) {
        return maintenanceRepo.findByDateBetween(debut, fin)
                .stream()
                .map(maintenanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MaintenanceDTO save(MaintenanceDTO dto) {
        Maintenance maintenance = maintenanceMapper.toEntity(dto);

        // Gestion des relations @ManyToOne via ID
        if (dto.getIdMachine() != null) {
            Machine machine = machineRepo.findById(dto.getIdMachine()).orElse(null);
            maintenance.setMachine(machine);
        }

        if (dto.getIdTechnicien() != null) {
            Technicien technicien = technicienRepo.findById(dto.getIdTechnicien()).orElse(null);
            maintenance.setTechnicien(technicien);
        }

        Maintenance saved = maintenanceRepo.save(maintenance);
        return maintenanceMapper.toDTO(saved);
    }

    public void delete(Long id) {
        maintenanceRepo.deleteById(id);
    }
}