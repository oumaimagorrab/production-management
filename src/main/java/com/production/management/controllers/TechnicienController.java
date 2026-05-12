package com.production.management.controllers;

import com.production.management.entities.Machine;
import com.production.management.entities.Technicien;
import com.production.management.services.TechnicienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Techniciens")
@CrossOrigin(origins = "*")
public class TechnicienController {

    @Autowired
    private TechnicienService technicienService;

    @PostMapping
    public ResponseEntity<Technicien> creerTechnicien(@RequestBody Technicien technicien) {
        Technicien nouveauTechnicien = technicienService.creerTechnicien(technicien);
        return new ResponseEntity<>(nouveauTechnicien, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Technicien>> getAllTechniciens() {
        return new ResponseEntity<>(technicienService.getAllTechniciens(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Technicien> getTechnicienById(@PathVariable Long id) {
        return technicienService.getTechnicienById(id)
                .map(technicien -> new ResponseEntity<>(technicien, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Technicien> mettreAJourTechnicien(@PathVariable Long id, @RequestBody Technicien technicien) {
        Technicien technicienMisAJour = technicienService.mettreAJourTechnicien(id, technicien);
        return new ResponseEntity<>(technicienMisAJour, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerTechnicien(@PathVariable Long id) {
        technicienService.supprimerTechnicien(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{technicienId}/affecter/{machineId}")
    public ResponseEntity<Technicien> affecterAMachine(@PathVariable Long technicienId, @PathVariable Long machineId) {
        Technicien technicien = technicienService.affecterAMachine(technicienId, machineId);
        return new ResponseEntity<>(technicien, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/retirer-affectation")
    public ResponseEntity<Technicien> retirerAffectation(@PathVariable Long id) {
        Technicien technicien = technicienService.retirerAffectation(id);
        return new ResponseEntity<>(technicien, HttpStatus.OK);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Technicien>> getTechniciensDisponibles() {
        return new ResponseEntity<>(technicienService.getTechniciensDisponibles(), HttpStatus.OK);
    }


    @GetMapping("/competence")
    public ResponseEntity<List<Technicien>> rechercherParCompetence(@RequestParam String competence) {
        return new ResponseEntity<>(technicienService.rechercherParCompetence(competence), HttpStatus.OK);
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<Technicien>> rechercherParNom(@RequestParam String nom) {
        return new ResponseEntity<>(technicienService.rechercherParNom(nom), HttpStatus.OK);
    }

    @GetMapping("/par-machine/{machineId}")
    public ResponseEntity<Technicien> getTechnicienParMachine(@PathVariable Long machineId) {
        return technicienService.getTechnicienParMachine(machineId)
                .map(technicien -> new ResponseEntity<>(technicien, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre-disponibles")
    public ResponseEntity<Long> compterDisponibles() {
        return new ResponseEntity<>(technicienService.compterDisponibles(), HttpStatus.OK);
    }
}
