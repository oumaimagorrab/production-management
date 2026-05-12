package com.production.management.controllers;

import com.production.management.entities.OrdreFabrication;
import com.production.management.services.OrdreFabricationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordres")
public class OrdreFabricationController {

    @Autowired
    private OrdreFabricationService ordreSer;

    @GetMapping
    public List<OrdreFabrication> getAll() {
        return ordreSer.getAll();
    }

    @GetMapping("/{id}")
    public OrdreFabrication getById(@PathVariable Long id) {
        return ordreSer.getOrdre(id).orElse(null);
    }

    @GetMapping("/statut/{statut}")
    public List<OrdreFabrication> getByStatut(@PathVariable OrdreFabrication.StatutOrdre statut) {
        return ordreSer.getByStatut(statut);
    }

    @GetMapping("/machine/{machineId}")
    public List<OrdreFabrication> getByMachine(@PathVariable Long machineId) {
        return ordreSer.getByMachine(machineId);
    }

    @GetMapping("/produit/{produitId}")
    public List<OrdreFabrication> getByProduit(@PathVariable Long produitId) {
        return ordreSer.getByProduit(produitId);
    }

    @GetMapping("/planifies")
    public List<OrdreFabrication> getPlanifies() {
        return ordreSer.getPlanifies();
    }

    @GetMapping("/retard")
    public List<OrdreFabrication> getEnRetard() {
        return ordreSer.getEnRetard();
    }

    @PostMapping
    public OrdreFabrication create(@RequestBody OrdreFabrication ordre) {
        return ordreSer.save(ordre);
    }

    @PutMapping("/{id}")
    public OrdreFabrication update(@PathVariable Long id, @RequestBody OrdreFabrication ordre) {
        ordre.setId(id);
        return ordreSer.save(ordre);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ordreSer.delete(id);
    }
}