package com.production.management.controllers;

import com.production.management.entities.Machine;
import com.production.management.entities.Machine.EtatMachine;
import com.production.management.services.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller REST pour la gestion des Machines
 * URL de base : /api/Machines
 */
@RestController
@RequestMapping("/api/Machines")
@CrossOrigin(origins = "*")
public class MachineController {

    @Autowired
    private MachineService machineService;

    // CREATE - POST /api/Machines
    @PostMapping
    public ResponseEntity<Machine> creerMachine(@RequestBody Machine machine) {
        Machine nouvelleMachine = machineService.creerMachine(machine);
        return new ResponseEntity<>(nouvelleMachine, HttpStatus.CREATED);
    }

    // READ ALL - GET /api/Machines
    @GetMapping
    public ResponseEntity<List<Machine>> getAllMachines() {
        return new ResponseEntity<>(machineService.getAllMachines(), HttpStatus.OK);
    }

    // READ ONE - GET /api/Machines/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Machine> getMachineById(@PathVariable Long id) {
        return machineService.getMachineById(id)
                .map(machine -> new ResponseEntity<>(machine, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE - PUT /api/Machines/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Machine> mettreAJourMachine(@PathVariable Long id, @RequestBody Machine machine) {
        Machine machineMisAJour = machineService.mettreAJourMachine(id, machine);
        return new ResponseEntity<>(machineMisAJour, HttpStatus.OK);
    }

    // DELETE - DELETE /api/Machines/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerMachine(@PathVariable Long id) {
        machineService.supprimerMachine(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Changer état - PATCH /api/Machines/{id}/etat?nouvelEtat=...
    @PatchMapping("/{id}/etat")
    public ResponseEntity<Machine> changerEtat(@PathVariable Long id, @RequestParam EtatMachine nouvelEtat) {
        Machine machine = machineService.changerEtat(id, nouvelEtat);
        return new ResponseEntity<>(machine, HttpStatus.OK);
    }

    // Machines disponibles - GET /api/Machines/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Machine>> getMachinesDisponibles() {
        return new ResponseEntity<>(machineService.getMachinesDisponibles(), HttpStatus.OK);
    }

    // Machines par état - GET /api/Machines/etat/{etat}
    @GetMapping("/etat/{etat}")
    public ResponseEntity<List<Machine>> getMachinesParEtat(@PathVariable EtatMachine etat) {
        return new ResponseEntity<>(machineService.getMachinesParEtat(etat), HttpStatus.OK);
    }

    // Machines maintenance prochaine - GET /api/Machines/maintenance-prochaine?date=2024-01-15
    @GetMapping("/maintenance-prochaine")
    public ResponseEntity<List<Machine>> getMachinesMaintenanceProchaine(@RequestParam String date) {
        LocalDate dateLimite = LocalDate.parse(date);
        return new ResponseEntity<>(machineService.getMachinesMaintenanceProchaine(dateLimite), HttpStatus.OK);
    }

    // Planifier maintenance - POST /api/Machines/{id}/planifier-maintenance?date=2024-06-01
    @PostMapping("/{id}/planifier-maintenance")
    public ResponseEntity<Machine> planifierMaintenance(@PathVariable Long id, @RequestParam String date) {
        LocalDate dateMaintenance = LocalDate.parse(date);
        Machine machine = machineService.planifierMaintenance(id, dateMaintenance);
        return new ResponseEntity<>(machine, HttpStatus.OK);
    }

    // Machines sans technicien - GET /api/Machines/sans-technicien
    @GetMapping("/sans-technicien")
    public ResponseEntity<List<Machine>> getMachinesSansTechnicien() {
        return new ResponseEntity<>(machineService.getMachinesSansTechnicien(), HttpStatus.OK);
    }

    // Machines avec technicien - GET /api/Machines/avec-technicien
    @GetMapping("/avec-technicien")
    public ResponseEntity<List<Machine>> getMachinesAvecTechnicien() {
        return new ResponseEntity<>(machineService.getMachinesAvecTechnicien(), HttpStatus.OK);
    }

    // Statistiques par état - GET /api/Machines/statistiques/{etat}
    @GetMapping("/statistiques/{etat}")
    public ResponseEntity<Long> compterParEtat(@PathVariable EtatMachine etat) {
        return new ResponseEntity<>(machineService.compterParEtat(etat), HttpStatus.OK);
    }
}
