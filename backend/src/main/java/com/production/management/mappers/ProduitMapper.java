package com.production.management.mappers;

import com.production.management.dtos.ProduitDTO;
import com.production.management.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProduitMapper {

    @Autowired
    private OrdreFabricationMapper ordreMapper;

    public ProduitDTO toDTO(Produit produit) {
        if (produit == null) return null;

        ProduitDTO dto = new ProduitDTO();
        dto.setId(produit.getId());
        dto.setNom(produit.getNom());
        dto.setType(produit.getType());
        dto.setStock(produit.getStock());
        dto.setFournisseur(produit.getFournisseur());

        // Gestion de la liste @OneToMany
        if (produit.getOrdresFabrication() != null) {
            dto.setListeOrdres(
                produit.getOrdresFabrication()
                    .stream()
                    .map(ordreMapper::toDTO)
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Produit toEntity(ProduitDTO dto) {
        if (dto == null) return null;

        Produit produit = new Produit();
        produit.setId(dto.getId());
        produit.setNom(dto.getNom());
        produit.setType(dto.getType());
        produit.setStock(dto.getStock());
        produit.setFournisseur(dto.getFournisseur());

        return produit;
    }
}