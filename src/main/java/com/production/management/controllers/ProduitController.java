package com.production.management.controllers;

import com.production.management.dtos.ProduitDTO;
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
    public List<ProduitDTO> getAll() {
        return produitSer.getAll();
    }

    @GetMapping("/{id}")
    public ProduitDTO getById(@PathVariable Long id) {
        return produitSer.getProduit(id).orElse(null);
    }

    @PostMapping
    public ProduitDTO create(@RequestBody ProduitDTO produit) {
        return produitSer.save(produit);
    }

    @PutMapping("/{id}")
    public ProduitDTO update(@PathVariable Long id, @RequestBody ProduitDTO produit) {
        produit.setId(id);
        return produitSer.save(produit);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        produitSer.delete(id);
    }
}