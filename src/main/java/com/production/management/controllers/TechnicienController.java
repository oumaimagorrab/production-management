package com.production.management.controllers;

import com.production.management.entities.Technicien;
import com.production.management.services.TechnicienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/techniciens")
public class TechnicienController {

    @Autowired
    private TechnicienService technicienSer;

    @GetMapping
    public List<Technicien> getAll() {
        return technicienSer.getAll();
    }

    @GetMapping("/{id}")
    public Technicien getById(@PathVariable Long id) {
        return technicienSer.getTechnicien(id).orElse(null);
    }

    @GetMapping("/sans-machine")
    public List<Technicien> getSansMachine() {
        return technicienSer.getSansMachine();
    }

    @GetMapping("/machine/{machineId}")
    public Technicien getByMachine(@PathVariable Long machineId) {
        return technicienSer.getByMachine(machineId).orElse(null);
    }

    @PostMapping
    public Technicien create(@RequestBody Technicien technicien) {
        return technicienSer.save(technicien);
    }

    @PutMapping("/{id}")
    public Technicien update(@PathVariable Long id, @RequestBody Technicien technicien) {
        technicien.setId(id);
        return technicienSer.save(technicien);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        technicienSer.delete(id);
    }
}