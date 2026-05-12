package com.production.management.controllers;

import com.production.management.entities.OrdreFabrication;
import com.production.management.entities.OrdreFabrication.StatutOrdre;
import com.production.management.services.OrdreFabricationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/OrdresFabrication")
@CrossOrigin(origins = "*")
public class OrdreFabricationController {

    @Autowired
    private OrdreFabricationService ordreService;

    @PostMapping
    public ResponseEntity<OrdreFabrication> creerOrdre(@RequestParam Long produitId,
                                                         @RequestParam Integer quantite,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                         @RequestParam Long machineId) {
        OrdreFabrication nouvelOrdre = ordreService.creerOrdre(produitId, quantite, date, machineId);
        return new ResponseEntity<>(nouvelOrdre, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrdreFabrication>> getAllOrdres() {
        return new ResponseEntity<>(ordreService.getAllOrdres(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdreFabrication> getOrdreById(@PathVariable Long id) {
        return ordreService.getOrdreById(id)
                .map(ordre -> new ResponseEntity<>(ordre, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdreFabrication> mettreAJourOrdre(@PathVariable Long id, @RequestBody OrdreFabrication ordre) {
        OrdreFabrication ordreMisAJour = ordreService.mettreAJourOrdre(id, ordre);
        return new ResponseEntity<>(ordreMisAJour, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerOrdre(@PathVariable Long id) {
        ordreService.supprimerOrdre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/demarrer")
    public ResponseEntity<OrdreFabrication> demarrerProduction(@PathVariable Long id) {
        OrdreFabrication ordre = ordreService.demarrerProduction(id);
        return new ResponseEntity<>(ordre, HttpStatus.OK);
    }

    @PostMapping("/{id}/terminer")
    public ResponseEntity<OrdreFabrication> terminerProduction(@PathVariable Long id) {
        OrdreFabrication ordre = ordreService.terminerProduction(id);
        return new ResponseEntity<>(ordre, HttpStatus.OK);
    }

    @PostMapping("/{id}/annuler")
    public ResponseEntity<OrdreFabrication> annulerOrdre(@PathVariable Long id) {
        OrdreFabrication ordre = ordreService.annulerOrdre(id);
        return new ResponseEntity<>(ordre, HttpStatus.OK);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<OrdreFabrication>> getOrdresParStatut(@PathVariable StatutOrdre statut) {
        return new ResponseEntity<>(ordreService.getOrdresParStatut(statut), HttpStatus.OK);
    }

    @GetMapping("/retard")
    public ResponseEntity<List<OrdreFabrication>> getOrdresEnRetard() {
        return new ResponseEntity<>(ordreService.getOrdresEnRetard(), HttpStatus.OK);
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<OrdreFabrication>> getOrdresParMachine(@PathVariable Long machineId) {
        return new ResponseEntity<>(ordreService.getOrdresParMachine(machineId), HttpStatus.OK);
    }

    @GetMapping("/produit/{produitId}")
    public ResponseEntity<List<OrdreFabrication>> getOrdresParProduit(@PathVariable Long produitId) {
        return new ResponseEntity<>(ordreService.getOrdresParProduit(produitId), HttpStatus.OK);
    }

    @GetMapping("/planning")
    public ResponseEntity<List<OrdreFabrication>> getPlanning(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return new ResponseEntity<>(ordreService.getPlanning(debut, fin), HttpStatus.OK);
    }

    @GetMapping("/statistiques/{statut}")
    public ResponseEntity<Long> compterParStatut(@PathVariable StatutOrdre statut) {
        return new ResponseEntity<>(ordreService.compterParStatut(statut), HttpStatus.OK);
    }
}
