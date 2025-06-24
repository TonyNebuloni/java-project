package com.sophiaynovcampus.model;

import javax.persistence.*;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
@Entity
@Table(name = "supports")
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    // Relation inverse, un support peut avoir plusieurs jeux
    @OneToMany(mappedBy = "support", cascade = CascadeType.ALL)
    private Set<JeuVideo> jeux;

    // Constructeurs
    public Support() {}
    public Support(String nom) {
        this.nom = nom;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Set<JeuVideo> getJeux() { return jeux; }
    public void setJeux(Set<JeuVideo> jeux) { this.jeux = jeux; }
}
