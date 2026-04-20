package com.production.management.controllers;

import com.production.management.entities.Machine;
import com.production.management.entities.Technicien;
import com.production.management.services.TechnicienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des Techniciens
 * URL de base : /api/Techniciens
 */
@RestController
@RequestMapping("/api/Techniciens")
@CrossOrigin(origins = "*")
public class TechnicienController {

    @Autowired
    private TechnicienService technicienService;

    // CREATE - POST /api/Techniciens
    @PostMapping
    public ResponseEntity<Technicien> creerTechnicien(@RequestBody Technicien technicien) {
        Technicien nouveauTechnicien = technicienService.creerTechnicien(technicien);
        return new ResponseEntity<>(nouveauTechnicien, HttpStatus.CREATED);
    }

    // READ ALL - GET /api/Techniciens
    @GetMapping
    public ResponseEntity<List<Technicien>> getAllTechniciens() {
        return new ResponseEntity<>(technicienService.getAllTechniciens(), HttpStatus.OK);
    }

    // READ ONE - GET /api/Techniciens/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Technicien> getTechnicienById(@PathVariable Long id) {
        return technicienService.getTechnicienById(id)
                .map(technicien -> new ResponseEntity<>(technicien, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE - PUT /api/Techniciens/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Technicien> mettreAJourTechnicien(@PathVariable Long id, @RequestBody Technicien technicien) {
        Technicien technicienMisAJour = technicienService.mettreAJourTechnicien(id, technicien);
        return new ResponseEntity<>(technicienMisAJour, HttpStatus.OK);
    }

    // DELETE - DELETE /api/Techniciens/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerTechnicien(@PathVariable Long id) {
        technicienService.supprimerTechnicien(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Affecter à machine - POST /api/Techniciens/{technicienId}/affecter/{machineId}
    @PostMapping("/{technicienId}/affecter/{machineId}")
    public ResponseEntity<Technicien> affecterAMachine(@PathVariable Long technicienId, @PathVariable Long machineId) {
        Technicien technicien = technicienService.affecterAMachine(technicienId, machineId);
        return new ResponseEntity<>(technicien, HttpStatus.OK);
    }

    // Retirer affectation - DELETE /api/Techniciens/{id}/retirer-affectation
    @DeleteMapping("/{id}/retirer-affectation")
    public ResponseEntity<Technicien> retirerAffectation(@PathVariable Long id) {
        Technicien technicien = technicienService.retirerAffectation(id);
        return new ResponseEntity<>(technicien, HttpStatus.OK);
    }

    // Techniciens disponibles - GET /api/Techniciens/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Technicien>> getTechniciensDisponibles() {
        return new ResponseEntity<>(technicienService.getTechniciensDisponibles(), HttpStatus.OK);
    }

    // Recherche par compétence - GET /api/Techniciens/competence?comp=mecanique
    @GetMapping("/competence")
    public ResponseEntity<List<Technicien>> rechercherParCompetence(@RequestParam String competence) {
        return new ResponseEntity<>(technicienService.rechercherParCompetence(competence), HttpStatus.OK);
    }

    // Recherche par nom - GET /api/Techniciens/recherche?nom=Jean
    @GetMapping("/recherche")
    public ResponseEntity<List<Technicien>> rechercherParNom(@RequestParam String nom) {
        return new ResponseEntity<>(technicienService.rechercherParNom(nom), HttpStatus.OK);
    }

    // Technicien par machine - GET /api/Techniciens/par-machine/{machineId}
    @GetMapping("/par-machine/{machineId}")
    public ResponseEntity<Technicien> getTechnicienParMachine(@PathVariable Long machineId) {
        return technicienService.getTechnicienParMachine(machineId)
                .map(technicien -> new ResponseEntity<>(technicien, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Nombre disponibles - GET /api/Techniciens/nombre-disponibles
    @GetMapping("/nombre-disponibles")
    public ResponseEntity<Long> compterDisponibles() {
        return new ResponseEntity<>(technicienService.compterDisponibles(), HttpStatus.OK);
    }
}
