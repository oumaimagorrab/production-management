package com.production.management.services;

import com.production.management.entities.OrdreFabrication;
import com.production.management.repository.OrdreFabricationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrdreFabricationService {

    @Autowired
    private OrdreFabricationRepository ordreRepo;

    public Optional<OrdreFabrication> getOrdre(Long id) {
        return ordreRepo.findById(id);
    }

    public List<OrdreFabrication> getAll() {
        return ordreRepo.findAll();
    }

    public List<OrdreFabrication> getByStatut(OrdreFabrication.StatutOrdre statut) {
        return ordreRepo.findByStatut(statut);
    }

    public List<OrdreFabrication> getByMachine(Long machineId) {
        return ordreRepo.findByMachineId(machineId);
    }

    public List<OrdreFabrication> getByProduit(Long produitId) {
        return ordreRepo.findByProduitId(produitId);
    }

    public List<OrdreFabrication> getPlanifies() {
        return ordreRepo.findByDateGreaterThanEqual(LocalDate.now());
    }

    public List<OrdreFabrication> getEnRetard() {
        return ordreRepo.findByDateBeforeAndStatutNot(LocalDate.now(), OrdreFabrication.StatutOrdre.TERMINE);
    }

    public OrdreFabrication save(OrdreFabrication ordre) {
        return ordreRepo.save(ordre);
    }

    public void delete(Long id) {
        ordreRepo.deleteById(id);
    }
}