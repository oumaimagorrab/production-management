package com.production.management.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "techniciens")
public class Technicien {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @ElementCollection
    @CollectionTable(name = "technicien_competences", joinColumns = @JoinColumn(name = "technicien_id"))
    @Column(name = "competence")
    private List<String> competences;
    
    // Relation One-to-One : un technicien peut être assigné à une machine spécifique
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_assignee_id", unique = true)
    private Machine machineAssignee;
    
    // Relation One-to-Many : un technicien peut effectuer plusieurs maintenances
    @OneToMany(mappedBy = "technicien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Maintenance> maintenances;

}
