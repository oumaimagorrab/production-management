package com.production.management.mappers;

import com.production.management.dtos.MachineDTO;
import com.production.management.entities.Machine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MachineMapper {

    @Autowired
    private OrdreFabricationMapper ordreMapper;

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    public MachineDTO toDTO(Machine machine) {
        if (machine == null) return null;

        MachineDTO dto = new MachineDTO();
        dto.setId(machine.getId());
        dto.setNom(machine.getNom());
        dto.setEtat(machine.getEtat());
        dto.setMaintenanceProchaine(machine.getMaintenanceProchaine());

        // ID de la relation @OneToOne inverse
        if (machine.getTechnicienAssigne() != null) {
            dto.setIdTechnicienAssigne(machine.getTechnicienAssigne().getId());
        }

        // Gestion des listes @OneToMany
        if (machine.getOrdresFabrication() != null) {
            dto.setListeOrdres(
                machine.getOrdresFabrication()
                    .stream()
                    .map(ordreMapper::toDTO)
                    .collect(Collectors.toList())
            );
        }

        if (machine.getMaintenances() != null) {
            dto.setListeMaintenances(
                machine.getMaintenances()
                    .stream()
                    .map(maintenanceMapper::toDTO)
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Machine toEntity(MachineDTO dto) {
        if (dto == null) return null;

        Machine machine = new Machine();
        machine.setId(dto.getId());
        machine.setNom(dto.getNom());
        machine.setEtat(dto.getEtat());
        machine.setMaintenanceProchaine(dto.getMaintenanceProchaine());

        return machine;
    }
}