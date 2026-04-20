package com.production.management.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordres_fabrication")
public class OrdreFabrication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Relation Many-to-One : plusieurs ordres peuvent concerner le même produit
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
    
    @Column(nullable = false)
    private int quantite;
    
    @Column(nullable = false)
    private LocalDate date;
    
    // Relation Many-to-One : plusieurs ordres peuvent utiliser la même machine
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutOrdre statut;
    
    // Énumération pour le statut de l'ordre de fabrication
    public enum StatutOrdre {
        PLANIFIE,      // Ordre planifié, en attente
        EN_COURS,      // Production en cours
        TERMINE,       // Production terminée avec succès
        ANNULE,        // Ordre annulé
        EN_RETARD      // Production en retard
    }

}
