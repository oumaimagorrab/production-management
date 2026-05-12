package com.production.management.controllers;

import com.production.management.entities.Produit;
import com.production.management.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Produits")
@CrossOrigin(origins = "*") // À adapter selon votre configuration CORS
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @PostMapping
    public ResponseEntity<Produit> creerProduit(@RequestBody Produit produit) {
        Produit nouveauProduit = produitService.creerProduit(produit);
        return new ResponseEntity<>(nouveauProduit, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        return new ResponseEntity<>(produitService.getAllProduits(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id)
                .map(produit -> new ResponseEntity<>(produit, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> mettreAJourProduit(@PathVariable Long id, @RequestBody Produit produit) {
        Produit produitMisAJour = produitService.mettreAJourProduit(id, produit);
        return new ResponseEntity<>(produitMisAJour, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) {
        produitService.supprimerProduit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<Produit>> rechercherParNom(@RequestParam String nom) {
        return new ResponseEntity<>(produitService.rechercherParNom(nom), HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Produit>> rechercherParType(@PathVariable String type) {
        return new ResponseEntity<>(produitService.rechercherParType(type), HttpStatus.OK);
    }

    @GetMapping("/stock-faible")
    public ResponseEntity<List<Produit>> getProduitsStockFaible(@RequestParam(defaultValue = "10") Integer seuil) {
        return new ResponseEntity<>(produitService.getProduitsStockFaible(seuil), HttpStatus.OK);
    }

    @GetMapping("/rupture-stock")
    public ResponseEntity<List<Produit>> getProduitsEnRupture() {
        return new ResponseEntity<>(produitService.getProduitsEnRupture(), HttpStatus.OK);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Produit> mettreAJourStock(@PathVariable Long id, @RequestParam Integer nouveauStock) {
        Produit produit = produitService.mettreAJourStock(id, nouveauStock);
        return new ResponseEntity<>(produit, HttpStatus.OK);
    }
}
