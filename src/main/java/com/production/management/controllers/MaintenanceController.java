package com.production.management.controllers;

import com.production.management.entities.Maintenance;
import com.production.management.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceSer;

    @GetMapping
    public List<Maintenance> getAll() {
        return maintenanceSer.getAll();
    }

    @GetMapping("/{id}")
    public Maintenance getById(@PathVariable Long id) {
        return maintenanceSer.getMaintenance(id).orElse(null);
    }

    @GetMapping("/machine/{machineId}")
    public List<Maintenance> getByMachine(@PathVariable Long machineId) {
        return maintenanceSer.getByMachine(machineId);
    }

    @GetMapping("/technicien/{technicienId}")
    public List<Maintenance> getByTechnicien(@PathVariable Long technicienId) {
        return maintenanceSer.getByTechnicien(technicienId);
    }

    @GetMapping("/type/{type}")
    public List<Maintenance> getByType(@PathVariable Maintenance.TypeMaintenance type) {
        return maintenanceSer.getByType(type);
    }

    @GetMapping("/planifiees")
    public List<Maintenance> getPlanifiees() {
        return maintenanceSer.getPlanifiees();
    }

    @GetMapping("/entre-dates")
    public List<Maintenance> getEntreDates(@RequestParam LocalDate debut, @RequestParam LocalDate fin) {
        return maintenanceSer.getEntreDates(debut, fin);
    }

    @PostMapping
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return maintenanceSer.save(maintenance);
    }

    @PutMapping("/{id}")
    public Maintenance update(@PathVariable Long id, @RequestBody Maintenance maintenance) {
        maintenance.setId(id);
        return maintenanceSer.save(maintenance);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        maintenanceSer.delete(id);
    }
}