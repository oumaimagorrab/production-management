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
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incrément (MySQL gère l’id automatiquement)
    private Long id;
    
    @Column(nullable = false, unique = true) //ne peut pas être NULL, 2 machines ne peuvent pas avoir le même nom
    private String nom;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)//type fixe (ex: ACTIVE, EN_PANNE, MAINTENANCE), stocké en texte dans la base
    private EtatMachine etat;
    
    @Column(name = "maintenance_prochaine")
    private LocalDate maintenanceProchaine;
    
    // Relation One-to-Many : une machine peut avoir plusieurs ordres de fabrication
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdreFabrication> ordresFabrication;
    
    // Relation One-to-Many : une machine peut avoir plusieurs maintenances
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Maintenance> maintenances;
    
    // Relation One-to-One : un technicien peut être assigné à une machine
    @JsonIgnore
    @OneToOne(mappedBy = "machineAssignee", fetch = FetchType.LAZY)
    private Technicien technicienAssigne;
    
    public enum EtatMachine {
        DISPONIBLE,
        EN_PANNE,
        EN_PRODUCTION,  
        EN_MAINTENANCE,    
    }

       
    
}
