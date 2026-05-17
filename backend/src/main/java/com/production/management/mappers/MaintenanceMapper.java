package com.production.management.mappers;

import com.production.management.dtos.MaintenanceDTO;
import com.production.management.entities.Maintenance;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceMapper {

    public MaintenanceDTO toDTO(Maintenance maintenance) {
        if (maintenance == null) return null;

        MaintenanceDTO dto = new MaintenanceDTO();
        dto.setId(maintenance.getId());
        dto.setDate(maintenance.getDate());
        dto.setType(maintenance.getType());

        // IDs des relations @ManyToOne
        if (maintenance.getMachine() != null) {
            dto.setIdMachine(maintenance.getMachine().getId());
        }

        if (maintenance.getTechnicien() != null) {
            dto.setIdTechnicien(maintenance.getTechnicien().getId());
        }

        return dto;
    }

    public Maintenance toEntity(MaintenanceDTO dto) {
        if (dto == null) return null;

        Maintenance maintenance = new Maintenance();
        maintenance.setId(dto.getId());
        maintenance.setDate(dto.getDate());
        maintenance.setType(dto.getType());

        return maintenance;
    }
}