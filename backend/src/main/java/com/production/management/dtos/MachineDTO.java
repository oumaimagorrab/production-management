package com.production.management.dtos;

import com.production.management.entities.Machine;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class MachineDTO {

    private Long id;
    private String nom;
    private Machine.EtatMachine etat;
    private LocalDate maintenanceProchaine;

    private Long idTechnicienAssigne;

    private List<OrdreFabricationDTO> listeOrdres = new ArrayList<OrdreFabricationDTO>();
    private List<MaintenanceDTO> listeMaintenances = new ArrayList<MaintenanceDTO>();

}