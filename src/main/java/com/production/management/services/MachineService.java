package com.production.management.services;

import com.production.management.dtos.MachineDTO;
import com.production.management.entities.Machine;
import com.production.management.entities.Technicien;
import com.production.management.mappers.MachineMapper;
import com.production.management.repository.MachineRepository;
import com.production.management.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MachineService {

    @Autowired
    private MachineRepository machineRepo;

    @Autowired
    private TechnicienRepository technicienRepo;

    @Autowired
    private MachineMapper machineMapper;

    public Optional<MachineDTO> getMachine(Long id) {
        return machineRepo.findById(id).map(machineMapper::toDTO);
    }

    public List<MachineDTO> getAll() {
        return machineRepo.findAll()
                .stream()
                .map(machineMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MachineDTO> getByEtat(Machine.EtatMachine etat) {
        return machineRepo.findByEtat(etat)
                .stream()
                .map(machineMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MachineDTO save(MachineDTO dto) {
        Machine machine = machineMapper.toEntity(dto);

        // Gestion de la relation @OneToOne inverse via ID
        if (dto.getIdTechnicienAssigne() != null) {
            Technicien technicien = technicienRepo.findById(dto.getIdTechnicienAssigne()).orElse(null);
            machine.setTechnicienAssigne(technicien);
        }

        Machine saved = machineRepo.save(machine);
        return machineMapper.toDTO(saved);
    }

    public void delete(Long id) {
        machineRepo.deleteById(id);
    }
}