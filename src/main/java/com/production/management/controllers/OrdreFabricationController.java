package com.production.management.controllers;

import com.production.management.dtos.OrdreFabricationDTO;
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
    public List<OrdreFabricationDTO> getAll() {
        return ordreSer.getAll();
    }

    @GetMapping("/{id}")
    public OrdreFabricationDTO getById(@PathVariable Long id) {
        return ordreSer.getOrdre(id).orElse(null);
    }

    @GetMapping("/statut/{statut}")
    public List<OrdreFabricationDTO> getByStatut(@PathVariable OrdreFabrication.StatutOrdre statut) {
        return ordreSer.getByStatut(statut);
    }

    @GetMapping("/planifies")
    public List<OrdreFabricationDTO> getPlanifies() {
        return ordreSer.getPlanifies();
    }

    @GetMapping("/retard")
    public List<OrdreFabricationDTO> getEnRetard() {
        return ordreSer.getEnRetard();
    }

    @PostMapping
    public OrdreFabricationDTO create(@RequestBody OrdreFabricationDTO ordre) {
        return ordreSer.save(ordre);
    }

    @PutMapping("/{id}")
    public OrdreFabricationDTO update(@PathVariable Long id, @RequestBody OrdreFabricationDTO ordre) {
        ordre.setId(id);
        return ordreSer.save(ordre);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ordreSer.delete(id);
    }
}