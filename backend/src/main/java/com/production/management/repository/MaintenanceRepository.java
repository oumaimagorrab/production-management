package com.production.management.repository;

import com.production.management.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    
    // Planification et suivi : maintenances par machine
    List<Maintenance> findByMachineId(Long machineId);
    
    // Planification et suivi : maintenances par technicien
    List<Maintenance> findByTechnicienId(Long technicienId);
    
    // Planification et suivi : maintenances par type
    List<Maintenance> findByType(Maintenance.TypeMaintenance type);
    
    // Planification : maintenances à venir
    List<Maintenance> findByDateGreaterThanEqual(LocalDate date);
    
    // Planification : maintenances entre deux dates
    List<Maintenance> findByDateBetween(LocalDate debut, LocalDate fin);
}