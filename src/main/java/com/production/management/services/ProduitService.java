package com.production.management.services;

import com.production.management.entities.Produit;
import com.production.management.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des produits
 * Implémente les opérations CRUD et les règles métier
 */
@Service
@Transactional
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    // Créer un nouveau produit
    public Produit creerProduit(Produit produit) {
        if (produitRepository.existsByNom(produit.getNom())) {
            throw new RuntimeException("Un produit avec ce nom existe déjà");
        }
        return produitRepository.save(produit);
    }

    // Récupérer tous les produits
    @Transactional(readOnly = true)
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    // Récupérer un produit par ID
    @Transactional(readOnly = true)
    public Optional<Produit> getProduitById(Long id) {
        return produitRepository.findById(id);
    }

    // Mettre à jour un produit
    public Produit mettreAJourProduit(Long id, Produit produitDetails) {
        Produit produit = produitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        
        produit.setNom(produitDetails.getNom());
        produit.setType(produitDetails.getType());
        produit.setStock(produitDetails.getStock());
        produit.setFournisseur(produitDetails.getFournisseur());
        
        return produitRepository.save(produit);
    }

    // Supprimer un produit
    public void supprimerProduit(Long id) {
        Produit produit = produitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        produitRepository.delete(produit);
    }

    // Rechercher par nom
    @Transactional(readOnly = true)
    public List<Produit> rechercherParNom(String nom) {
        return produitRepository.findByNomContainingIgnoreCase(nom);
    }

    // Rechercher par type
    @Transactional(readOnly = true)
    public List<Produit> rechercherParType(String type) {
        return produitRepository.findByType(type);
    }

    // Produits avec stock faible (alerte)
    @Transactional(readOnly = true)
    public List<Produit> getProduitsStockFaible(Integer seuil) {
        return produitRepository.findByStockLessThan(seuil);
    }

    // Produits en rupture de stock
    @Transactional(readOnly = true)
    public List<Produit> getProduitsEnRupture() {
        return produitRepository.findProduitsEnRupture();
    }

    // Mettre à jour le stock
    public Produit mettreAJourStock(Long id, Integer nouveauStock) {
        Produit produit = produitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        produit.setStock(nouveauStock);
        return produitRepository.save(produit);
    }
}
