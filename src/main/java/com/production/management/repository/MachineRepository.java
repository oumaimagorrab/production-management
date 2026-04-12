package com.production.management.repository;

import com.production.management.entities.Machine;
import com.production.management.entities.Machine.EtatMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des machines
 * Inclut les requêtes pour la planification et le suivi
 */
@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    
    // Recherche par nom exact
    Optional<Machine> findByNom(String nom);
    
    // Recherche par état
    List<Machine> findByEtat(EtatMachine etat);
    
    // Recherche des machines nécessitant une maintenance prochaine
    List<Machine> findByMaintenanceProchaineBefore(LocalDate date);
    
    // Recherche des machines sans maintenance planifiée
    List<Machine> findByMaintenanceProchaineIsNull();
    
    // Recherche des machines par état avec ordre par date de maintenance
    List<Machine> findByEtatOrderByMaintenanceProchaineAsc(EtatMachine etat);
    
    // Vérifier si une machine existe par son nom
    boolean existsByNom(String nom);
    
    // Recherche des machines en maintenance ou hors service
    @Query("SELECT m FROM Machine m WHERE m.etat IN (:etats)")
    List<Machine> findByEtats(@Param("etats") List<EtatMachine> etats);
    
    // Compter les machines par état
    long countByEtat(EtatMachine etat);
    
    // Recherche des machines avec technicien assigné
    @Query("SELECT m FROM Machine m WHERE m.technicienAssigne IS NOT NULL")
    List<Machine> findMachinesAvecTechnicien();
    
    // Recherche des machines sans technicien assigné
    @Query("SELECT m FROM Machine m WHERE m.technicienAssigne IS NULL")
    List<Machine> findMachinesSansTechnicien();
}