package com.production.management.services;

import com.production.management.entities.Machine;
import com.production.management.entities.Technicien;
import com.production.management.repository.MachineRepository;
import com.production.management.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des techniciens
 * Gère les compétences et les affectations aux machines
 */
@Service
@Transactional
public class TechnicienService {

    @Autowired
    private TechnicienRepository technicienRepository;
    
    @Autowired
    private MachineRepository machineRepository;

    // Créer un nouveau technicien
    public Technicien creerTechnicien(Technicien technicien) {
        if (technicienRepository.existsByNom(technicien.getNom())) {
            throw new RuntimeException("Un technicien avec ce nom existe déjà");
        }
        return technicienRepository.save(technicien);
    }

    // Récupérer tous les techniciens
    @Transactional(readOnly = true)
    public List<Technicien> getAllTechniciens() {
        return technicienRepository.findAll();
    }

    // Récupérer un technicien par ID
    @Transactional(readOnly = true)
    public Optional<Technicien> getTechnicienById(Long id) {
        return technicienRepository.findById(id);
    }

    // Mettre à jour un technicien
    public Technicien mettreAJourTechnicien(Long id, Technicien technicienDetails) {
        Technicien technicien = technicienRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
        
        technicien.setNom(technicienDetails.getNom());
        technicien.setCompetences(technicienDetails.getCompetences());
        
        return technicienRepository.save(technicien);
    }

    // Supprimer un technicien
    public void supprimerTechnicien(Long id) {
        Technicien technicien = technicienRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
        technicienRepository.delete(technicien);
    }

    // Affecter un technicien à une machine
    public Technicien affecterAMachine(Long technicienId, Long machineId) {
        Technicien technicien = technicienRepository.findById(technicienId)
            .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
        
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        
        // Vérifier si la machine est déjà assignée
        if (machine.getTechnicienAssigne() != null) {
            throw new RuntimeException("Cette machine a déjà un technicien assigné");
        }
        
        technicien.setMachineAssignee(machine);
        return technicienRepository.save(technicien);
    }

    // Retirer l'affectation d'une machine
    public Technicien retirerAffectation(Long technicienId) {
        Technicien technicien = technicienRepository.findById(technicienId)
            .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
        
        technicien.setMachineAssignee(null);
        return technicienRepository.save(technicien);
    }

    // Techniciens disponibles (sans machine)
    @Transactional(readOnly = true)
    public List<Technicien> getTechniciensDisponibles() {
        return technicienRepository.findTechniciensDisponibles();
    }

    // Rechercher par compétence
    @Transactional(readOnly = true)
    public List<Technicien> rechercherParCompetence(String competence) {
        return technicienRepository.findByCompetence(competence);
    }

    // Rechercher par nom
    @Transactional(readOnly = true)
    public List<Technicien> rechercherParNom(String nom) {
        return technicienRepository.findByNomContainingIgnoreCase(nom);
    }

    // Obtenir le technicien assigné à une machine
    @Transactional(readOnly = true)
    public Optional<Technicien> getTechnicienParMachine(Long machineId) {
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        return technicienRepository.findByMachineAssignee(machine);
    }

    // Nombre de techniciens disponibles
    @Transactional(readOnly = true)
    public long compterDisponibles() {
        return technicienRepository.countTechniciensDisponibles();
    }
}
