package com.production.management.services;

import com.production.management.entities.Produit;
import com.production.management.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepo;

    public Optional<Produit> getProduit(Long id) {
        return produitRepo.findById(id);
    }

    public List<Produit> getAll() {
        return produitRepo.findAll();
    }

    public Produit save(Produit produit) {
        return produitRepo.save(produit);
    }

    public void delete(Long id) {
        produitRepo.deleteById(id);
    }
}