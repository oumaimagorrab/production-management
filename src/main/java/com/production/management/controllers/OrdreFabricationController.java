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

/**
 * Controller REST pour la gestion des Ordres de Fabrication
 * URL de base : /api/OrdresFabrication
 */
@RestController
@RequestMapping("/api/OrdresFabrication")
@CrossOrigin(origins = "*")
public class OrdreFabricationController {

    @Autowired
    private OrdreFabricationService ordreService;

    // CREATE - POST /api/OrdresFabrication
    @PostMapping
    public ResponseEntity<OrdreFabrication> creerOrdre(@RequestParam Long produitId,
                                                         @RequestParam Integer quantite,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                         @RequestParam Long machineId) {
        OrdreFabrication nouvelOrdre = ordreService.creerOrdre(produitId, quantite, date, machineId);
        return new ResponseEntity<>(nouvelOrdre, HttpStatus.CREATED);
    }

    // READ ALL - GET /api/OrdresFabrication
    @GetMapping
    public ResponseEntity<List<OrdreFabrication>> getAllOrdres() {
        return new ResponseEntity<>(ordreService.getAllOrdres(), HttpStatus.OK);
    }

    // READ ONE - GET /api/OrdresFabrication/{id}
    @GetMapping("/{id}")
    public ResponseEntity<OrdreFabrication> getOrdreById(@PathVariable Long id) {
        return ordreService.getOrdreById(id)
                .map(ordre -> new ResponseEntity<>(ordre, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE - PUT /api/OrdresFabrication/{id}
    @PutMapping("/{id}")
    public ResponseEntity<OrdreFabrication> mettreAJourOrdre(@PathVariable Long id, @RequestBody OrdreFabrication ordre) {
        OrdreFabrication ordreMisAJour = ordreService.mettreAJourOrdre(id, ordre);
        return new ResponseEntity<>(ordreMisAJour, HttpStatus.OK);
    }

    // DELETE - DELETE /api/OrdresFabrication/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerOrdre(@PathVariable Long id) {
        ordreService.supprimerOrdre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Démarrer production - POST /api/OrdresFabrication/{id}/demarrer
    @PostMapping("/{id}/demarrer")
    public ResponseEntity<OrdreFabrication> demarrerProduction(@PathVariable Long id) {
        OrdreFabrication ordre = ordreService.demarrerProduction(id);
        return new ResponseEntity<>(ordre, HttpStatus.OK);
    }

    // Terminer production - POST /api/OrdresFabrication/{id}/terminer
    @PostMapping("/{id}/terminer")
    public ResponseEntity<OrdreFabrication> terminerProduction(@PathVariable Long id) {
        OrdreFabrication ordre = ordreService.terminerProduction(id);
        return new ResponseEntity<>(ordre, HttpStatus.OK);
    }

    // Annuler ordre - POST /api/OrdresFabrication/{id}/annuler
    @PostMapping("/{id}/annuler")
    public ResponseEntity<OrdreFabrication> annulerOrdre(@PathVariable Long id) {
        OrdreFabrication ordre = ordreService.annulerOrdre(id);
        return new ResponseEntity<>(ordre, HttpStatus.OK);
    }

    // Ordres par statut - GET /api/OrdresFabrication/statut/{statut}
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<OrdreFabrication>> getOrdresParStatut(@PathVariable StatutOrdre statut) {
        return new ResponseEntity<>(ordreService.getOrdresParStatut(statut), HttpStatus.OK);
    }

    // Ordres en retard - GET /api/OrdresFabrication/retard
    @GetMapping("/retard")
    public ResponseEntity<List<OrdreFabrication>> getOrdresEnRetard() {
        return new ResponseEntity<>(ordreService.getOrdresEnRetard(), HttpStatus.OK);
    }

    // Ordres par machine - GET /api/OrdresFabrication/machine/{machineId}
    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<OrdreFabrication>> getOrdresParMachine(@PathVariable Long machineId) {
        return new ResponseEntity<>(ordreService.getOrdresParMachine(machineId), HttpStatus.OK);
    }

    // Ordres par produit - GET /api/OrdresFabrication/produit/{produitId}
    @GetMapping("/produit/{produitId}")
    public ResponseEntity<List<OrdreFabrication>> getOrdresParProduit(@PathVariable Long produitId) {
        return new ResponseEntity<>(ordreService.getOrdresParProduit(produitId), HttpStatus.OK);
    }

    // Planning - GET /api/OrdresFabrication/planning?debut=2024-01-01&fin=2024-12-31
    @GetMapping("/planning")
    public ResponseEntity<List<OrdreFabrication>> getPlanning(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return new ResponseEntity<>(ordreService.getPlanning(debut, fin), HttpStatus.OK);
    }

    // Statistiques par statut - GET /api/OrdresFabrication/statistiques/{statut}
    @GetMapping("/statistiques/{statut}")
    public ResponseEntity<Long> compterParStatut(@PathVariable StatutOrdre statut) {
        return new ResponseEntity<>(ordreService.compterParStatut(statut), HttpStatus.OK);
    }
}
