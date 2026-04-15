package com.production.management.services;

import com.production.management.entities.Machine;
import com.production.management.entities.Machine.EtatMachine;
import com.production.management.entities.OrdreFabrication;
import com.production.management.entities.OrdreFabrication.StatutOrdre;
import com.production.management.entities.Produit;
import com.production.management.repository.MachineRepository;
import com.production.management.repository.OrdreFabricationRepository;
import com.production.management.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la planification et le suivi des ordres de fabrication
 * Gère le workflow de production
 */
@Service
@Transactional
public class OrdreFabricationService {

    @Autowired
    private OrdreFabricationRepository ordreRepository;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private MachineRepository machineRepository;

    // Créer un nouvel ordre de fabrication
    public OrdreFabrication creerOrdre(Long produitId, Integer quantite, 
                                       LocalDate date, Long machineId) {
        Produit produit = produitRepository.findById(produitId)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        
        // Vérifier si la machine est disponible
        if (machine.getEtat() != EtatMachine.DISPONIBLE) {
            throw new RuntimeException("La machine n'est pas disponible");
        }
        
        // Vérifier si la machine est déjà occupée à cette date
        boolean occupee = ordreRepository.existsByMachineAndDateAndStatutNot(
            machine, date, StatutOrdre.ANNULE);
        if (occupee) {
            throw new RuntimeException("La machine est déjà occupée à cette date");
        }
        
        OrdreFabrication ordre = new OrdreFabrication();
        ordre.setProduit(produit);
        ordre.setQuantite(quantite);
        ordre.setDate(date);
        ordre.setMachine(machine);
        ordre.setStatut(StatutOrdre.PLANIFIE);
        
        return ordreRepository.save(ordre);
    }

    // Récupérer tous les ordres
    @Transactional(readOnly = true)
    public List<OrdreFabrication> getAllOrdres() {
        return ordreRepository.findAll();
    }

    // Récupérer un ordre par ID
    @Transactional(readOnly = true)
    public Optional<OrdreFabrication> getOrdreById(Long id) {
        return ordreRepository.findById(id);
    }

    // Mettre à jour un ordre
    public OrdreFabrication mettreAJourOrdre(Long id, OrdreFabrication ordreDetails) {
        OrdreFabrication ordre = ordreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ordre non trouvé"));
        
        ordre.setQuantite(ordreDetails.getQuantite());
        ordre.setDate(ordreDetails.getDate());
        ordre.setStatut(ordreDetails.getStatut());
        
        return ordreRepository.save(ordre);
    }

    // Supprimer un ordre
    public void supprimerOrdre(Long id) {
        OrdreFabrication ordre = ordreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ordre non trouvé"));
        ordreRepository.delete(ordre);
    }

    // Démarrer la production (changer statut)
    public OrdreFabrication demarrerProduction(Long id) {
        OrdreFabrication ordre = ordreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ordre non trouvé"));
        
        if (ordre.getStatut() != StatutOrdre.PLANIFIE) {
            throw new RuntimeException("L'ordre doit être planifié pour démarrer");
        }
        
        ordre.setStatut(StatutOrdre.EN_COURS);
        
        // Mettre la machine en production
        Machine machine = ordre.getMachine();
        machine.setEtat(EtatMachine.EN_PRODUCTION);
        machineRepository.save(machine);
        
        return ordreRepository.save(ordre);
    }

    // Terminer la production
    public OrdreFabrication terminerProduction(Long id) {
        OrdreFabrication ordre = ordreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ordre non trouvé"));
        
        if (ordre.getStatut() != StatutOrdre.EN_COURS) {
            throw new RuntimeException("L'ordre doit être en cours pour être terminé");
        }
        
        ordre.setStatut(StatutOrdre.TERMINE);
        
        // Libérer la machine
        Machine machine = ordre.getMachine();
        machine.setEtat(EtatMachine.DISPONIBLE);
        machineRepository.save(machine);
        
        // Mettre à jour le stock du produit
        Produit produit = ordre.getProduit();
        produit.setStock(produit.getStock() + ordre.getQuantite());
        produitRepository.save(produit);
        
        return ordreRepository.save(ordre);
    }

    // Annuler un ordre
    public OrdreFabrication annulerOrdre(Long id) {
        OrdreFabrication ordre = ordreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ordre non trouvé"));
        
        ordre.setStatut(StatutOrdre.ANNULE);
        
        // Libérer la machine si en cours
        if (ordre.getStatut() == StatutOrdre.EN_COURS) {
            Machine machine = ordre.getMachine();
            machine.setEtat(EtatMachine.DISPONIBLE);
            machineRepository.save(machine);
        }
        
        return ordreRepository.save(ordre);
    }

    // Ordres par statut
    @Transactional(readOnly = true)
    public List<OrdreFabrication> getOrdresParStatut(StatutOrdre statut) {
        return ordreRepository.findByStatut(statut);
    }

    // Ordres en retard
    @Transactional(readOnly = true)
    public List<OrdreFabrication> getOrdresEnRetard() {
        return ordreRepository.findOrdresEnRetard(LocalDate.now());
    }

    // Ordres par machine
    @Transactional(readOnly = true)
    public List<OrdreFabrication> getOrdresParMachine(Long machineId) {
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new RuntimeException("Machine non trouvée"));
        return ordreRepository.findByMachine(machine);
    }

    // Ordres par produit
    @Transactional(readOnly = true)
    public List<OrdreFabrication> getOrdresParProduit(Long produitId) {
        Produit produit = produitRepository.findById(produitId)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return ordreRepository.findByProduit(produit);
    }

    // Ordres pour une période
    @Transactional(readOnly = true)
    public List<OrdreFabrication> getOrdresPeriode(LocalDate debut, LocalDate fin) {
        return ordreRepository.findByDateBetween(debut, fin);
    }

    // Planning des ordres (triés par date)
    @Transactional(readOnly = true)
    public List<OrdreFabrication> getPlanning(LocalDate debut, LocalDate fin) {
        return ordreRepository.findOrdresPlanifiesPeriode(debut, fin);
    }

    // Statistiques par statut
    @Transactional(readOnly = true)
    public long compterParStatut(StatutOrdre statut) {
        return ordreRepository.countByStatut(statut);
    }
}