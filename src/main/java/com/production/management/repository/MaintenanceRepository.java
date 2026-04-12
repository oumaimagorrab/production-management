package com.production.management.repository;

import com.production.management.entities.Machine;
import com.production.management.entities.Maintenance;
import com.production.management.entities.Maintenance.TypeMaintenance;
import com.production.management.entities.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour la planification et le suivi des maintenances
 * Gère les interventions sur les machines
 */
@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    
    // Recherche par machine
    List<Maintenance> findByMachine(Machine machine);
    
    // Recherche par technicien
    List<Maintenance> findByTechnicien(Technicien technicien);
    
    // Recherche par type de maintenance
    List<Maintenance> findByType(TypeMaintenance type);
    
    // Recherche par date
    List<Maintenance> findByDate(LocalDate date);
    
    // Recherche par plage de dates
    List<Maintenance> findByDateBetween(LocalDate debut, LocalDate fin);
    
    // Recherche des maintenances futures planifiées
    @Query("SELECT m FROM Maintenance m WHERE m.date >= :date ORDER BY m.date ASC")
    List<Maintenance> findMaintenancesPlanifiees(@Param("date") LocalDate date);
    
    // Recherche des maintenances par machine et période
    List<Maintenance> findByMachineAndDateBetween(Machine machine, LocalDate debut, LocalDate fin);
    
    // Recherche des maintenances par technicien et période
    List<Maintenance> findByTechnicienAndDateBetween(Technicien technicien, LocalDate debut, LocalDate fin);
    
    // Compter les maintenances par type
    long countByType(TypeMaintenance type);
    
    // Compter les maintenances par machine
    long countByMachine(Machine machine);
    
    // Recherche de la dernière maintenance d'une machine
    @Query("SELECT m FROM Maintenance m WHERE m.machine = :machine ORDER BY m.date DESC")
    List<Maintenance> findDernieresMaintenancesMachine(@Param("machine") Machine machine);
    
    // Vérifier si un technicien a des maintenances planifiées à une date
    boolean existsByTechnicienAndDate(Technicien technicien, LocalDate date);
    
    // Recherche des maintenances préventives à venir
    @Query("SELECT m FROM Maintenance m WHERE m.type = 'PREVENTIVE' AND m.date >= :date")
    List<Maintenance> findMaintenancesPreventivesAVenir(@Param("date") LocalDate date);
}
