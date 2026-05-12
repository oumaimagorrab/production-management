package com.production.management.controllers;

import com.production.management.entities.Produit;
import com.production.management.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitSer;

    @GetMapping
    public List<Produit> getAll() {
        return produitSer.getAll();
    }

    @GetMapping("/{id}")
    public Produit getById(@PathVariable Long id) {
        return produitSer.getProduit(id).orElse(null);
    }

    @PostMapping
    public Produit create(@RequestBody Produit produit) {
        return produitSer.save(produit);
    }

    @PutMapping("/{id}")
    public Produit update(@PathVariable Long id, @RequestBody Produit produit) {
        produit.setId(id);
        return produitSer.save(produit);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        produitSer.delete(id);
    }
}