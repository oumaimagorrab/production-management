package com.production.management.mappers;

import com.production.management.dtos.OrdreFabricationDTO;
import com.production.management.entities.OrdreFabrication;
import org.springframework.stereotype.Component;

@Component
public class OrdreFabricationMapper {

    public OrdreFabricationDTO toDTO(OrdreFabrication ordre) {
        if (ordre == null) return null;

        OrdreFabricationDTO dto = new OrdreFabricationDTO();
        dto.setId(ordre.getId());
        dto.setQuantite(ordre.getQuantite());
        dto.setDate(ordre.getDate());
        dto.setStatut(ordre.getStatut());

        // IDs des relations @ManyToOne
        if (ordre.getProduit() != null) {
            dto.setIdProduit(ordre.getProduit().getId());
        }

        if (ordre.getMachine() != null) {
            dto.setIdMachine(ordre.getMachine().getId());
        }

        return dto;
    }

    public OrdreFabrication toEntity(OrdreFabricationDTO dto) {
        if (dto == null) return null;

        OrdreFabrication ordre = new OrdreFabrication();
        ordre.setId(dto.getId());
        ordre.setQuantite(dto.getQuantite());
        ordre.setDate(dto.getDate());
        ordre.setStatut(dto.getStatut());

        return ordre;
    }
}