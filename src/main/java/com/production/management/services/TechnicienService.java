package com.production.management.services;

import com.production.management.entities.Technicien;
import com.production.management.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicienService {

    @Autowired
    private TechnicienRepository technicienRepo;

    public Optional<Technicien> getTechnicien(Long id) {
        return technicienRepo.findById(id);
    }

    public List<Technicien> getAll() {
        return technicienRepo.findAll();
    }

    public List<Technicien> getSansMachine() {
        return technicienRepo.findByMachineAssigneeIsNull();
    }

    public Optional<Technicien> getByMachine(Long machineId) {
        return technicienRepo.findByMachineAssigneeId(machineId);
    }

    public Technicien save(Technicien technicien) {
        return technicienRepo.save(technicien);
    }

    public void delete(Long id) {
        technicienRepo.deleteById(id);
    }
}