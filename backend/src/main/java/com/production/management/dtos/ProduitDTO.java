package com.production.management.dtos;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProduitDTO {

    private Long id;
    private String nom;
    private String type;
    private int stock;
    private String fournisseur;

    private List<OrdreFabricationDTO> listeOrdres = new ArrayList<OrdreFabricationDTO>();

}