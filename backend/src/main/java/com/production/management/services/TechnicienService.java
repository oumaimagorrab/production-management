package com.production.management.services;

import com.production.management.dtos.TechnicienDTO;
import com.production.management.entities.Machine;
import com.production.management.entities.Technicien;
import com.production.management.mappers.TechnicienMapper;
import com.production.management.repository.MachineRepository;
import com.production.management.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TechnicienService {

    @Autowired
    private TechnicienRepository technicienRepo;

    @Autowired
    private MachineRepository machineRepo;

    @Autowired
    private TechnicienMapper technicienMapper;

    public Optional<TechnicienDTO> getTechnicien(Long id) {
        return technicienRepo.findById(id).map(technicienMapper::toDTO);
    }

    public List<TechnicienDTO> getAll() {
        return technicienRepo.findAll()
                .stream()
                .map(technicienMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TechnicienDTO> getSansMachine() {
        return technicienRepo.findByMachineAssigneeIsNull()
                .stream()
                .map(technicienMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TechnicienDTO save(TechnicienDTO dto) {
        Technicien technicien = technicienMapper.toEntity(dto);

        // Gestion de la relation @OneToOne via ID
        if (dto.getIdMachineAssignee() != null) {
            Machine machine = machineRepo.findById(dto.getIdMachineAssignee()).orElse(null);
            technicien.setMachineAssignee(machine);
        }

        Technicien saved = technicienRepo.save(technicien);
        return technicienMapper.toDTO(saved);
    }

    public void delete(Long id) {
        technicienRepo.deleteById(id);
    }
}