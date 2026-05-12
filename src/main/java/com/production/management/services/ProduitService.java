package com.production.management.services;

import com.production.management.dtos.ProduitDTO;
import com.production.management.entities.Produit;
import com.production.management.mappers.ProduitMapper;
import com.production.management.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepo;

    @Autowired
    private ProduitMapper produitMapper;

    public Optional<ProduitDTO> getProduit(Long id) {
        return produitRepo.findById(id).map(produitMapper::toDTO);
    }

    public List<ProduitDTO> getAll() {
        return produitRepo.findAll()
                .stream()
                .map(produitMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProduitDTO save(ProduitDTO dto) {
        Produit produit = produitMapper.toEntity(dto);
        Produit saved = produitRepo.save(produit);
        return produitMapper.toDTO(saved);
    }

    public void delete(Long id) {
        produitRepo.deleteById(id);
    }
}