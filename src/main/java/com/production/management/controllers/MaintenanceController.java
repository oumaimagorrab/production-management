package com.production.management.controllers;

import com.production.management.entities.Maintenance;
import com.production.management.entities.Maintenance.TypeMaintenance;
import com.production.management.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/Maintenances")
@CrossOrigin(origins = "*")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<Maintenance> planifierMaintenance(@RequestParam Long machineId,
                                                            @RequestParam Long technicienId,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                            @RequestParam TypeMaintenance type) {
        Maintenance nouvelleMaintenance = maintenanceService.planifierMaintenance(machineId, technicienId, date, type);
        return new ResponseEntity<>(nouvelleMaintenance, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Maintenance>> getAllMaintenances() {
        return new ResponseEntity<>(maintenanceService.getAllMaintenances(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getMaintenanceById(@PathVariable Long id) {
        return maintenanceService.getMaintenanceById(id)
                .map(maintenance -> new ResponseEntity<>(maintenance, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> mettreAJourMaintenance(@PathVariable Long id, @RequestBody Maintenance maintenance) {
        Maintenance maintenanceMisAJour = maintenanceService.mettreAJourMaintenance(id, maintenance);
        return new ResponseEntity<>(maintenanceMisAJour, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerMaintenance(@PathVariable Long id) {
        maintenanceService.supprimerMaintenance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/demarrer")
    public ResponseEntity<Maintenance> demarrerMaintenance(@PathVariable Long id) {
        Maintenance maintenance = maintenanceService.demarrerMaintenance(id);
        return new ResponseEntity<>(maintenance, HttpStatus.OK);
    }

    @PostMapping("/{id}/terminer")
    public ResponseEntity<Maintenance> terminerMaintenance(@PathVariable Long id) {
        Maintenance maintenance = maintenanceService.terminerMaintenance(id);
        return new ResponseEntity<>(maintenance, HttpStatus.OK);
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Maintenance>> getMaintenancesParMachine(@PathVariable Long machineId) {
        return new ResponseEntity<>(maintenanceService.getMaintenancesParMachine(machineId), HttpStatus.OK);
    }

    @GetMapping("/technicien/{technicienId}")
    public ResponseEntity<List<Maintenance>> getMaintenancesParTechnicien(@PathVariable Long technicienId) {
        return new ResponseEntity<>(maintenanceService.getMaintenancesParTechnicien(technicienId), HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Maintenance>> getMaintenancesParType(@PathVariable TypeMaintenance type) {
        return new ResponseEntity<>(maintenanceService.getMaintenancesParType(type), HttpStatus.OK);
    }

    @GetMapping("/planifiees")
    public ResponseEntity<List<Maintenance>> getMaintenancesPlanifiees() {
        return new ResponseEntity<>(maintenanceService.getMaintenancesPlanifiees(), HttpStatus.OK);
    }

    @GetMapping("/preventives-a-venir")
    public ResponseEntity<List<Maintenance>> getMaintenancesPreventivesAVenir() {
        return new ResponseEntity<>(maintenanceService.getMaintenancesPreventivesAVenir(), HttpStatus.OK);
    }

    @GetMapping("/periode")
    public ResponseEntity<List<Maintenance>> getMaintenancesPeriode(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return new ResponseEntity<>(maintenanceService.getMaintenancesPeriode(debut, fin), HttpStatus.OK);
    }

    @GetMapping("/historique/{machineId}")
    public ResponseEntity<List<Maintenance>> getHistoriqueMachine(@PathVariable Long machineId) {
        return new ResponseEntity<>(maintenanceService.getHistoriqueMachine(machineId), HttpStatus.OK);
    }

    @GetMapping("/statistiques/type/{type}")
    public ResponseEntity<Long> compterParType(@PathVariable TypeMaintenance type) {
        return new ResponseEntity<>(maintenanceService.compterParType(type), HttpStatus.OK);
    }

    @GetMapping("/statistiques/machine/{machineId}")
    public ResponseEntity<Long> compterParMachine(@PathVariable Long machineId) {
        return new ResponseEntity<>(maintenanceService.compterParMachine(machineId), HttpStatus.OK);
    }
}
