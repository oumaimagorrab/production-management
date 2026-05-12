package com.production.management.mappers;

import com.production.management.dtos.TechnicienDTO;
import com.production.management.entities.Technicien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TechnicienMapper {

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    public TechnicienDTO toDTO(Technicien technicien) {
        if (technicien == null) return null;

        TechnicienDTO dto = new TechnicienDTO();
        dto.setId(technicien.getId());
        dto.setNom(technicien.getNom());
        dto.setCompetences(technicien.getCompetences());

        // ID de la relation @OneToOne
        if (technicien.getMachineAssignee() != null) {
            dto.setIdMachineAssignee(technicien.getMachineAssignee().getId());
        }

        // Gestion de la liste @OneToMany
        if (technicien.getMaintenances() != null) {
            dto.setListeMaintenances(
                technicien.getMaintenances()
                    .stream()
                    .map(maintenanceMapper::toDTO)
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Technicien toEntity(TechnicienDTO dto) {
        if (dto == null) return null;

        Technicien technicien = new Technicien();
        technicien.setId(dto.getId());
        technicien.setNom(dto.getNom());
        technicien.setCompetences(dto.getCompetences());

        return technicien;
    }
}