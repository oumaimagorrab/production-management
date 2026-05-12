package com.production.management.dtos;

import com.production.management.entities.Maintenance;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MaintenanceDTO {

    private Long id;
    private LocalDate date;
    private Maintenance.TypeMaintenance type;

    private Long idMachine;
    private Long idTechnicien;

}