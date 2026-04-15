package com.production.management.controllers;

import com.production.management.entities.Produit;
import com.production.management.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des Produits
 * URL de base : /api/produits
 */
@RestController
@RequestMapping("/api/Produits")
@CrossOrigin(origins = "*") // À adapter selon votre configuration CORS
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    // CREATE - POST /api/Produits
    @PostMapping
    public ResponseEntity<Produit> creerProduit(@RequestBody Produit produit) {
        Produit nouveauProduit = produitService.creerProduit(produit);
        return new ResponseEntity<>(nouveauProduit, HttpStatus.CREATED);
    }

    // READ ALL - GET /api/Produits
    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        return new ResponseEntity<>(produitService.getAllProduits(), HttpStatus.OK);
    }

    // READ ONE - GET /api/Produits/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id)
                .map(produit -> new ResponseEntity<>(produit, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE - PUT /api/Produits/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Produit> mettreAJourProduit(@PathVariable Long id, @RequestBody Produit produit) {
        Produit produitMisAJour = produitService.mettreAJourProduit(id, produit);
        return new ResponseEntity<>(produitMisAJour, HttpStatus.OK);
    }

    // DELETE - DELETE /api/Produits/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) {
        produitService.supprimerProduit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Recherche par nom - GET /api/Produits/recherche?nom=...
    @GetMapping("/recherche")
    public ResponseEntity<List<Produit>> rechercherParNom(@RequestParam String nom) {
        return new ResponseEntity<>(produitService.rechercherParNom(nom), HttpStatus.OK);
    }

    // Recherche par type - GET /api/Produits/type/{type}
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Produit>> rechercherParType(@PathVariable String type) {
        return new ResponseEntity<>(produitService.rechercherParType(type), HttpStatus.OK);
    }

    // Stock faible - GET /api/Produits/stock-faible?seuil=10
    @GetMapping("/stock-faible")
    public ResponseEntity<List<Produit>> getProduitsStockFaible(@RequestParam(defaultValue = "10") Integer seuil) {
        return new ResponseEntity<>(produitService.getProduitsStockFaible(seuil), HttpStatus.OK);
    }

    // Produits en rupture - GET /api/Produits/rupture-stock
    @GetMapping("/rupture-stock")
    public ResponseEntity<List<Produit>> getProduitsEnRupture() {
        return new ResponseEntity<>(produitService.getProduitsEnRupture(), HttpStatus.OK);
    }

    // Mise à jour stock - PATCH /api/Produits/{id}/stock
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Produit> mettreAJourStock(@PathVariable Long id, @RequestParam Integer nouveauStock) {
        Produit produit = produitService.mettreAJourStock(id, nouveauStock);
        return new ResponseEntity<>(produit, HttpStatus.OK);
    }
}
