package com.production.management.repository;

import com.production.management.entities.Machine;
import com.production.management.entities.OrdreFabrication;
import com.production.management.entities.OrdreFabrication.StatutOrdre;
import com.production.management.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour la planification et le suivi des ordres de fabrication
 * Méthodes pour la gestion de la production
 */
@Repository
public interface OrdreFabricationRepository extends JpaRepository<OrdreFabrication, Long> {
    
    // Recherche par statut
    List<OrdreFabrication> findByStatut(StatutOrdre statut);
    
    // Recherche par produit
    List<OrdreFabrication> findByProduit(Produit produit);
    
    // Recherche par machine
    List<OrdreFabrication> findByMachine(Machine machine);
    
    // Recherche par date
    List<OrdreFabrication> findByDate(LocalDate date);
    
    // Recherche par plage de dates
    List<OrdreFabrication> findByDateBetween(LocalDate debut, LocalDate fin);
    
    // Recherche des ordres en retard (date passée et statut non terminé)
    @Query("SELECT o FROM OrdreFabrication o WHERE o.date < :aujourdHui AND o.statut NOT IN ('TERMINE', 'ANNULE')")
    List<OrdreFabrication> findOrdresEnRetard(@Param("aujourdHui") LocalDate aujourdHui);
    
    // Recherche des ordres planifiés pour une machine à une date donnée
    List<OrdreFabrication> findByMachineAndDate(Machine machine, LocalDate date);
    
    // Recherche des ordres par statut et date
    List<OrdreFabrication> findByStatutAndDateBetween(StatutOrdre statut, LocalDate debut, LocalDate fin);
    
    // Compter les ordres par statut
    long countByStatut(StatutOrdre statut);
    
    // Recherche des ordres en cours pour une machine
    @Query("SELECT o FROM OrdreFabrication o WHERE o.machine = :machine AND o.statut = 'EN_COURS'")
    List<OrdreFabrication> findOrdresEnCoursParMachine(@Param("machine") Machine machine);
    
    // Recherche des ordres planifiés pour une période
    @Query("SELECT o FROM OrdreFabrication o WHERE o.date >= :debut AND o.date <= :fin ORDER BY o.date ASC")
    List<OrdreFabrication> findOrdresPlanifiesPeriode(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
    
    // Vérifier si une machine est occupée à une date donnée
    boolean existsByMachineAndDateAndStatutNot(Machine machine, LocalDate date, StatutOrdre statut);
}