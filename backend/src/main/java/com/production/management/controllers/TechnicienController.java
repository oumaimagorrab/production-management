package com.production.management.controllers;

import com.production.management.dtos.TechnicienDTO;
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
    public List<TechnicienDTO> getAll() {
        return technicienSer.getAll();
    }

    @GetMapping("/{id}")
    public TechnicienDTO getById(@PathVariable Long id) {
        return technicienSer.getTechnicien(id).orElse(null);
    }

    @GetMapping("/sans-machine")
    public List<TechnicienDTO> getSansMachine() {
        return technicienSer.getSansMachine();
    }

    @PostMapping
    public TechnicienDTO create(@RequestBody TechnicienDTO technicien) {
        return technicienSer.save(technicien);
    }

    @PutMapping("/{id}")
    public TechnicienDTO update(@PathVariable Long id, @RequestBody TechnicienDTO technicien) {
        technicien.setId(id);
        return technicienSer.save(technicien);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        technicienSer.delete(id);
    }
}