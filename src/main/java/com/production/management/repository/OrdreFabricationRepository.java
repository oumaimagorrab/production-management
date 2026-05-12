package com.production.management.repository;

import com.production.management.entities.OrdreFabrication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdreFabricationRepository extends JpaRepository<OrdreFabrication, Long> {
    
    // Planification et suivi : ordres par statut
    List<OrdreFabrication> findByStatut(OrdreFabrication.StatutOrdre statut);
    
    // Planification et suivi : ordres par machine
    List<OrdreFabrication> findByMachineId(Long machineId);
    
    // Planification et suivi : ordres par produit
    List<OrdreFabrication> findByProduitId(Long produitId);
    
    // Planification : ordres à venir (date >= aujourd'hui)
    List<OrdreFabrication> findByDateGreaterThanEqual(LocalDate date);
    
    // Planification : ordres en retard (date passée et non terminé)
    List<OrdreFabrication> findByDateBeforeAndStatutNot(LocalDate date, OrdreFabrication.StatutOrdre statut);
}