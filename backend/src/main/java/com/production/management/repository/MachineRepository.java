package com.production.management.repository;

import com.production.management.entities.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    
    // Gestion des affectations : trouver machines par état
    List<Machine> findByEtat(Machine.EtatMachine etat);
    
    // Vérifier si une machine est disponible pour affectation
    Optional<Machine> findByIdAndEtat(Long id, Machine.EtatMachine etat);
}