package com.production.management.dtos;

import com.production.management.entities.OrdreFabrication;
import lombok.Data;
import java.time.LocalDate;

@Data
public class OrdreFabricationDTO {

    private Long id;
    private int quantite;
    private LocalDate date;
    private OrdreFabrication.StatutOrdre statut;

    private Long idProduit;
    private Long idMachine;

}