package com.production.management.controllers;

import com.production.management.dtos.MachineDTO;
import com.production.management.entities.Machine;
import com.production.management.services.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    @Autowired
    private MachineService machineSer;

    @GetMapping
    public List<MachineDTO> getAll() {
        return machineSer.getAll();
    }

    @GetMapping("/{id}")
    public MachineDTO getById(@PathVariable Long id) {
        return machineSer.getMachine(id).orElse(null);
    }

    @GetMapping("/etat/{etat}")
    public List<MachineDTO> getByEtat(@PathVariable Machine.EtatMachine etat) {
        return machineSer.getByEtat(etat);
    }

    @PostMapping
    public MachineDTO create(@RequestBody MachineDTO machine) {
        return machineSer.save(machine);
    }

    @PutMapping("/{id}")
    public MachineDTO update(@PathVariable Long id, @RequestBody MachineDTO machine) {
        machine.setId(id);
        return machineSer.save(machine);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        machineSer.delete(id);
    }
}