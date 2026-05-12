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

@RestController
@RequestMapping("/api/Machines")
@CrossOrigin(origins = "*")
public class MachineController {

    @Autowired
    private MachineService machineService;

    @PostMapping
    public ResponseEntity<Machine> creerMachine(@RequestBody Machine machine) {
        Machine nouvelleMachine = machineService.creerMachine(machine);
        return new ResponseEntity<>(nouvelleMachine, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Machine>> getAllMachines() {
        return new ResponseEntity<>(machineService.getAllMachines(), HttpStatus.OK);
    }

    @GetMapping("/{id}") //URI
    public ResponseEntity<Machine> getMachineById(@PathVariable Long id) {
        return machineService.getMachineById(id)
                .map(machine -> new ResponseEntity<>(machine, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Machine> mettreAJourMachine(@PathVariable Long id, @RequestBody Machine machine) {
        Machine machineMisAJour = machineService.mettreAJourMachine(id, machine);
        return new ResponseEntity<>(machineMisAJour, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerMachine(@PathVariable Long id) {
        machineService.supprimerMachine(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/etat")
    public ResponseEntity<Machine> changerEtat(@PathVariable Long id, @RequestParam EtatMachine nouvelEtat) {
        Machine machine = machineService.changerEtat(id, nouvelEtat);
        return new ResponseEntity<>(machine, HttpStatus.OK);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Machine>> getMachinesDisponibles() {
        return new ResponseEntity<>(machineService.getMachinesDisponibles(), HttpStatus.OK);
    }

    @GetMapping("/etat/{etat}")
    public ResponseEntity<List<Machine>> getMachinesParEtat(@PathVariable EtatMachine etat) {
        return new ResponseEntity<>(machineService.getMachinesParEtat(etat), HttpStatus.OK);
    }

    @GetMapping("/maintenance-prochaine")
    public ResponseEntity<List<Machine>> getMachinesMaintenanceProchaine(@RequestParam String date) {
        LocalDate dateLimite = LocalDate.parse(date);
        return new ResponseEntity<>(machineService.getMachinesMaintenanceProchaine(dateLimite), HttpStatus.OK);
    }

    @PostMapping("/{id}/planifier-maintenance")
    public ResponseEntity<Machine> planifierMaintenance(@PathVariable Long id, @RequestParam String date) {
        LocalDate dateMaintenance = LocalDate.parse(date);
        Machine machine = machineService.planifierMaintenance(id, dateMaintenance);
        return new ResponseEntity<>(machine, HttpStatus.OK);
    }

    @GetMapping("/sans-technicien")
    public ResponseEntity<List<Machine>> getMachinesSansTechnicien() {
        return new ResponseEntity<>(machineService.getMachinesSansTechnicien(), HttpStatus.OK);
    }

    @GetMapping("/avec-technicien")
    public ResponseEntity<List<Machine>> getMachinesAvecTechnicien() {
        return new ResponseEntity<>(machineService.getMachinesAvecTechnicien(), HttpStatus.OK);
    }

    @GetMapping("/statistiques/{etat}")
    public ResponseEntity<Long> compterParEtat(@PathVariable EtatMachine etat) {
        return new ResponseEntity<>(machineService.compterParEtat(etat), HttpStatus.OK);
    }
}
