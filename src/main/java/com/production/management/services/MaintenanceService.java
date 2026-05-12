package com.production.management.services;

import com.production.management.entities.Maintenance;
import com.production.management.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepo;

    public Optional<Maintenance> getMaintenance(Long id) {
        return maintenanceRepo.findById(id);
    }

    public List<Maintenance> getAll() {
        return maintenanceRepo.findAll();
    }

    public List<Maintenance> getByMachine(Long machineId) {
        return maintenanceRepo.findByMachineId(machineId);
    }

    public List<Maintenance> getByTechnicien(Long technicienId) {
        return maintenanceRepo.findByTechnicienId(technicienId);
    }

    public List<Maintenance> getByType(Maintenance.TypeMaintenance type) {
        return maintenanceRepo.findByType(type);
    }

    public List<Maintenance> getPlanifiees() {
        return maintenanceRepo.findByDateGreaterThanEqual(LocalDate.now());
    }

    public List<Maintenance> getEntreDates(LocalDate debut, LocalDate fin) {
        return maintenanceRepo.findByDateBetween(debut, fin);
    }

    public Maintenance save(Maintenance maintenance) {
        return maintenanceRepo.save(maintenance);
    }

    public void delete(Long id) {
        maintenanceRepo.deleteById(id);
    }
}