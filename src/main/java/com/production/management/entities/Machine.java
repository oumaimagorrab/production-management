package com.production.management.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "machines")
public class Machine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(nullable = false, unique = true) 
    private String nom;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EtatMachine etat;
    
    @Column(name = "maintenance_prochaine")
    private LocalDate maintenanceProchaine;
    
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdreFabrication> ordresFabrication;
    
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Maintenance> maintenances;
    
    @OneToOne(mappedBy = "machineAssignee", fetch = FetchType.LAZY)
    private Technicien technicienAssigne;
    
    public enum EtatMachine {
        DISPONIBLE,
        EN_PANNE,
        EN_PRODUCTION,  
        EN_MAINTENANCE,    
    }

       
    
}
