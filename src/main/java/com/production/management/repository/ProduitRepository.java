package com.production.management.repository;


import com.production.management.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des produits
 * Fournit les opérations CRUD et les requêtes personnalisées
 */
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    
    // Recherche par nom exact
    Optional<Produit> findByNom(String nom);
    
    // Recherche par nom contenant (insensible à la casse)
    List<Produit> findByNomContainingIgnoreCase(String nom);
    
    // Recherche par type
    List<Produit> findByType(String type);
    
    // Recherche des produits avec stock faible
    List<Produit> findByStockLessThan(Integer seuil);
    
    // Recherche par fournisseur
    List<Produit> findByFournisseurContainingIgnoreCase(String fournisseur);
    
    // Vérifier si un produit existe par son nom
    boolean existsByNom(String nom);
    
    // Recherche des produits en rupture de stock
    @Query("SELECT p FROM Produit p WHERE p.stock = 0")
    List<Produit> findProduitsEnRupture();
    
    // Recherche des produits triés par stock croissant
    List<Produit> findAllByOrderByStockAsc();
}
