package com.production.management.services;

import com.production.management.entities.Machine;
import com.production.management.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {

    @Autowired
    private MachineRepository machineRepo;

    public Optional<Machine> getMachine(Long id) {
        return machineRepo.findById(id);
    }

    public List<Machine> getAll() {
        return machineRepo.findAll();
    }

    public List<Machine> getByEtat(Machine.EtatMachine etat) {
        return machineRepo.findByEtat(etat);
    }

    public Optional<Machine> getDisponible(Long id) {
        return machineRepo.findByIdAndEtat(id, Machine.EtatMachine.DISPONIBLE);
    }

    public Machine save(Machine machine) {
        return machineRepo.save(machine);
    }

    public void delete(Long id) {
        machineRepo.deleteById(id);
    }
}