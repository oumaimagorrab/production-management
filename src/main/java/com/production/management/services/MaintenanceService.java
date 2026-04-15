package com.production.management.services;

import com.production.management.entities.Machine;
import com.production.management.entities.Machine.EtatMachine;
import com.production.management.entities.Maintenance;
import com.production.management.entities.Maintenance.TypeMaintenance;
import com.production.management.entities.Technicien;
import com.production.management.repository.MachineRepository;
import com.production.management.repository.MaintenanceRepository;
import com.production.management.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la planification et le suivi des maintenances
 * Gère les interventions sur les machines
 */
@Service
@Transactional
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    
    @Autowired
    private MachineRepository machineRepository;
    
    @Autowired
    private TechnicienRepository technicienRepository;

    // Planifier une maintenance
    public Maintenance planifierMaintenance(Long machineId, Long technicienId, 
                                            LocalDate date, TypeMaintenance type) {
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        
        Technicien technicien = technicienRepository.findById(technicienId)
            .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
        
        // Vérifier si le technicien est disponible à cette date
        if (maintenanceRepository.existsByTechnicienAndDate(technicien, date)) {
            throw new RuntimeException("Le technicien a déjà une maintenance à cette date");
        }
        
        Maintenance maintenance = new Maintenance();
        maintenance.setMachine(machine);
        maintenance.setTechnicien(technicien);
        maintenance.setDate(date);
        maintenance.setType(type);
        
        // Si maintenance préventive, mettre à jour la date de prochaine maintenance
        if (type == TypeMaintenance.PREVENTIVE) {
            machine.setMaintenanceProchaine(date.plusMonths(3)); // Prochaine dans 3 mois
            machineRepository.save(machine);
        }
        
        return maintenanceRepository.save(maintenance);
    }

    // Récupérer toutes les maintenances
    @Transactional(readOnly = true)
    public List<Maintenance> getAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    // Récupérer une maintenance par ID
    @Transactional(readOnly = true)
    public Optional<Maintenance> getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id);
    }

    // Mettre à jour une maintenance
    public Maintenance mettreAJourMaintenance(Long id, Maintenance maintenanceDetails) {
        Maintenance maintenance = maintenanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Maintenance non trouvée"));
        
        maintenance.setDate(maintenanceDetails.getDate());
        maintenance.setType(maintenanceDetails.getType());
        
        return maintenanceRepository.save(maintenance);
    }

    // Supprimer une maintenance
    public void supprimerMaintenance(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Maintenance non trouvée"));
        maintenanceRepository.delete(maintenance);
    }

    // Démarrer une maintenance (mettre machine en maintenance)
    public Maintenance demarrerMaintenance(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Maintenance non trouvée"));
        
        Machine machine = maintenance.getMachine();
        machine.setEtat(EtatMachine.EN_MAINTENANCE);
        machineRepository.save(machine);
        
        return maintenance;
    }

    // Terminer une maintenance (libérer la machine)
    public Maintenance terminerMaintenance(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Maintenance non trouvée"));
        
        Machine machine = maintenance.getMachine();
        machine.setEtat(EtatMachine.DISPONIBLE);
        
        // Mettre à jour la prochaine maintenance préventive
        if (maintenance.getType() == TypeMaintenance.PREVENTIVE) {
            machine.setMaintenanceProchaine(LocalDate.now().plusMonths(3));
        }
        
        machineRepository.save(machine);
        return maintenance;
    }

    // Maintenances par machine
    @Transactional(readOnly = true)
    public List<Maintenance> getMaintenancesParMachine(Long machineId) {
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        return maintenanceRepository.findByMachine(machine);
    }

    // Maintenances par technicien
    @Transactional(readOnly = true)
    public List<Maintenance> getMaintenancesParTechnicien(Long technicienId) {
        Technicien technicien = technicienRepository.findById(technicienId)
            .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
        return maintenanceRepository.findByTechnicien(technicien);
    }

    // Maintenances par type
    @Transactional(readOnly = true)
    public List<Maintenance> getMaintenancesParType(TypeMaintenance type) {
        return maintenanceRepository.findByType(type);
    }

    // Maintenances planifiées (futures)
    @Transactional(readOnly = true)
    public List<Maintenance> getMaintenancesPlanifiees() {
        return maintenanceRepository.findMaintenancesPlanifiees(LocalDate.now());
    }

    // Maintenances préventives à venir
    @Transactional(readOnly = true)
    public List<Maintenance> getMaintenancesPreventivesAVenir() {
        return maintenanceRepository.findMaintenancesPreventivesAVenir(LocalDate.now());
    }

    // Maintenances pour une période
    @Transactional(readOnly = true)
    public List<Maintenance> getMaintenancesPeriode(LocalDate debut, LocalDate fin) {
        return maintenanceRepository.findByDateBetween(debut, fin);
    }

    // Historique des maintenances d'une machine
    @Transactional(readOnly = true)
    public List<Maintenance> getHistoriqueMachine(Long machineId) {
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        return maintenanceRepository.findDernieresMaintenancesMachine(machine);
    }

    // Statistiques par type
    @Transactional(readOnly = true)
    public long compterParType(TypeMaintenance type) {
        return maintenanceRepository.countByType(type);
    }

    // Statistiques par machine
    @Transactional(readOnly = true)
    public long compterParMachine(Long machineId) {
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        return maintenanceRepository.countByMachine(machine);
    }
}
