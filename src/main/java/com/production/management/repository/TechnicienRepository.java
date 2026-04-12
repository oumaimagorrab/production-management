package com.production.management.repository;

import com.production.management.entities.Machine;
import com.production.management.entities.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des techniciens
 * Gère les affectations et les compétences
 */
@Repository
public interface TechnicienRepository extends JpaRepository<Technicien, Long> {
    
    // Recherche par nom exact
    Optional<Technicien> findByNom(String nom);
    
    // Recherche par nom contenant
    List<Technicien> findByNomContainingIgnoreCase(String nom);
    
    // Recherche du technicien assigné à une machine spécifique
    Optional<Technicien> findByMachineAssignee(Machine machine);
    
    // Recherche des techniciens sans assignation machine
    @Query("SELECT t FROM Technicien t WHERE t.machineAssignee IS NULL")
    List<Technicien> findTechniciensDisponibles();
    
    // Recherche des techniciens avec une compétence spécifique
    @Query("SELECT t FROM Technicien t JOIN t.competences c WHERE LOWER(c) = LOWER(:competence)")
    List<Technicien> findByCompetence(@Param("competence") String competence);
    
    // Recherche des techniciens ayant effectué des maintenances
    @Query("SELECT DISTINCT t FROM Technicien t JOIN t.maintenances m")
    List<Technicien> findTechniciensAvecMaintenances();
    
    // Vérifier si un technicien existe par son nom
    boolean existsByNom(String nom);
    
    // Compter les techniciens disponibles (sans machine assignée)
    @Query("SELECT COUNT(t) FROM Technicien t WHERE t.machineAssignee IS NULL")
    long countTechniciensDisponibles();
}
