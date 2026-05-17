package com.production.management.services;

import com.production.management.dtos.OrdreFabricationDTO;
import com.production.management.entities.Machine;
import com.production.management.entities.OrdreFabrication;
import com.production.management.entities.Produit;
import com.production.management.mappers.OrdreFabricationMapper;
import com.production.management.repository.MachineRepository;
import com.production.management.repository.OrdreFabricationRepository;
import com.production.management.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdreFabricationService {

    @Autowired
    private OrdreFabricationRepository ordreRepo;

    @Autowired
    private ProduitRepository produitRepo;

    @Autowired
    private MachineRepository machineRepo;

    @Autowired
    private OrdreFabricationMapper ordreMapper;

    public Optional<OrdreFabricationDTO> getOrdre(Long id) {
        return ordreRepo.findById(id).map(ordreMapper::toDTO);
    }

    public List<OrdreFabricationDTO> getAll() {
        return ordreRepo.findAll()
                .stream()
                .map(ordreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrdreFabricationDTO> getByStatut(OrdreFabrication.StatutOrdre statut) {
        return ordreRepo.findByStatut(statut)
                .stream()
                .map(ordreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrdreFabricationDTO> getByMachine(Long machineId) {
        return ordreRepo.findByMachineId(machineId)
                .stream()
                .map(ordreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrdreFabricationDTO> getByProduit(Long produitId) {
        return ordreRepo.findByProduitId(produitId)
                .stream()
                .map(ordreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrdreFabricationDTO> getPlanifies() {
        return ordreRepo.findByDateGreaterThanEqual(LocalDate.now())
                .stream()
                .map(ordreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrdreFabricationDTO> getEnRetard() {
        return ordreRepo.findByDateBeforeAndStatutNot(LocalDate.now(), OrdreFabrication.StatutOrdre.TERMINE)
                .stream()
                .map(ordreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrdreFabricationDTO save(OrdreFabricationDTO dto) {
        OrdreFabrication ordre = ordreMapper.toEntity(dto);

        // Gestion des relations @ManyToOne via ID
        if (dto.getIdProduit() != null) {
            Produit produit = produitRepo.findById(dto.getIdProduit()).orElse(null);
            ordre.setProduit(produit);
        }

        if (dto.getIdMachine() != null) {
            Machine machine = machineRepo.findById(dto.getIdMachine()).orElse(null);
            ordre.setMachine(machine);
        }

        OrdreFabrication saved = ordreRepo.save(ordre);
        return ordreMapper.toDTO(saved);
    }

    public void delete(Long id) {
        ordreRepo.deleteById(id);
    }
}