package com.production.management.repository;

import com.production.management.entities.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicienRepository extends JpaRepository<Technicien, Long> {
    
    // Gestion des affectations : techniciens sans machine assignée
    List<Technicien> findByMachineAssigneeIsNull();
    
    // Trouver technicien par sa machine assignée
    Optional<Technicien> findByMachineAssigneeId(Long machineId);
}