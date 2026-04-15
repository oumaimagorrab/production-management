package com.production.management.services;

import com.production.management.entities.Machine;
import com.production.management.entities.Machine.EtatMachine;
import com.production.management.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des machines
 * Gère les affectations et la planification des maintenances
 */
@Service
@Transactional
public class MachineService {

    @Autowired
    private MachineRepository machineRepository;

    // Créer une nouvelle machine
    public Machine creerMachine(Machine machine) {
        if (machineRepository.existsByNom(machine.getNom())) {
            throw new RuntimeException("Une machine avec ce nom existe déjà");
        }
        machine.setEtat(EtatMachine.DISPONIBLE);
        return machineRepository.save(machine);
    }

    // Récupérer toutes les machines
    @Transactional(readOnly = true)
    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }

    // Récupérer une machine par ID
    @Transactional(readOnly = true)
    public Optional<Machine> getMachineById(Long id) {
        return machineRepository.findById(id);
    }

    // Mettre à jour une machine
    public Machine mettreAJourMachine(Long id, Machine machineDetails) {
        Machine machine = machineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        
        machine.setNom(machineDetails.getNom());
        machine.setEtat(machineDetails.getEtat());
        machine.setMaintenanceProchaine(machineDetails.getMaintenanceProchaine());
        
        return machineRepository.save(machine);
    }

    // Supprimer une machine
    public void supprimerMachine(Long id) {
        Machine machine = machineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        machineRepository.delete(machine);
    }

    // Changer l'état d'une machine
    public Machine changerEtat(Long id, EtatMachine nouvelEtat) {
        Machine machine = machineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        machine.setEtat(nouvelEtat);
        return machineRepository.save(machine);
    }

    // Machines disponibles
    @Transactional(readOnly = true)
    public List<Machine> getMachinesDisponibles() {
        return machineRepository.findByEtat(EtatMachine.DISPONIBLE);
    }

    // Machines par état
    @Transactional(readOnly = true)
    public List<Machine> getMachinesParEtat(EtatMachine etat) {
        return machineRepository.findByEtat(etat);
    }

    // Machines nécessitant maintenance prochaine
    @Transactional(readOnly = true)
    public List<Machine> getMachinesMaintenanceProchaine(LocalDate dateLimite) {
        return machineRepository.findByMaintenanceProchaineBefore(dateLimite);
    }

    // Planifier une maintenance
    public Machine planifierMaintenance(Long id, LocalDate dateMaintenance) {
        Machine machine = machineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        machine.setMaintenanceProchaine(dateMaintenance);
        return machineRepository.save(machine);
    }

    // Machines sans technicien assigné
    @Transactional(readOnly = true)
    public List<Machine> getMachinesSansTechnicien() {
        return machineRepository.findMachinesSansTechnicien();
    }

    // Machines avec technicien assigné
    @Transactional(readOnly = true)
    public List<Machine> getMachinesAvecTechnicien() {
        return machineRepository.findMachinesAvecTechnicien();
    }

    // Statistiques par état
    @Transactional(readOnly = true)
    public long compterParEtat(EtatMachine etat) {
        return machineRepository.countByEtat(etat);
    }
}
