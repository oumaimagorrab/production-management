package com.production.management.dtos;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class TechnicienDTO {

    private Long id;
    private String nom;
    private List<String> competences;

    private Long idMachineAssignee;

    private List<MaintenanceDTO> listeMaintenances = new ArrayList<MaintenanceDTO>();

}