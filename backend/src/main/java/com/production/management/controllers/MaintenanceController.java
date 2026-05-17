package com.production.management.controllers;

import com.production.management.dtos.MaintenanceDTO;
import com.production.management.entities.Maintenance;
import com.production.management.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceSer;

    @GetMapping
    public List<MaintenanceDTO> getAll() {
        return maintenanceSer.getAll();
    }

    @GetMapping("/{id}")
    public MaintenanceDTO getById(@PathVariable Long id) {
        return maintenanceSer.getMaintenance(id).orElse(null);
    }

    @GetMapping("/machine/{machineId}")
    public List<MaintenanceDTO> getByMachine(@PathVariable Long machineId) {
        return maintenanceSer.getByMachine(machineId);
    }

    @GetMapping("/type/{type}")
    public List<MaintenanceDTO> getByType(@PathVariable Maintenance.TypeMaintenance type) {
        return maintenanceSer.getByType(type);
    }

    @GetMapping("/planifiees")
    public List<MaintenanceDTO> getPlanifiees() {
        return maintenanceSer.getPlanifiees();
    }

    @PostMapping
    public MaintenanceDTO create(@RequestBody MaintenanceDTO maintenance) {
        return maintenanceSer.save(maintenance);
    }

    @PutMapping("/{id}")
    public MaintenanceDTO update(@PathVariable Long id, @RequestBody MaintenanceDTO maintenance) {
        maintenance.setId(id);
        return maintenanceSer.save(maintenance);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        maintenanceSer.delete(id);
    }
}