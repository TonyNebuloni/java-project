package com.sophiaynovcampus.model;

import javax.persistence.*;

@Entity
@Table(name = "jeux_video")
public class JeuVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    private String editeur;
    private String developpeur;
    private Integer anneeSortie;

    @ManyToOne
    @JoinColumn(name = "support_id")
    private Support support;

    private Integer noteMetacritic;

    private String jaquette; // URL ou chemin local

    // Constructeurs
    public JeuVideo() {}

    // Getters / Setters
    public Long getId() { return id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getEditeur() { return editeur; }
    public void setEditeur(String editeur) { this.editeur = editeur; }

    public String getDeveloppeur() { return developpeur; }
    public void setDeveloppeur(String developpeur) { this.developpeur = developpeur; }

    public Integer getAnneeSortie() { return anneeSortie; }
    public void setAnneeSortie(Integer anneeSortie) { this.anneeSortie = anneeSortie; }

    public Support getSupport() { return support; }
    public void setSupport(Support support) { this.support = support; }

    public Integer getNoteMetacritic() { return noteMetacritic; }
    public void setNoteMetacritic(Integer noteMetacritic) { this.noteMetacritic = noteMetacritic; }

    public String getJaquette() { return jaquette; }
    public void setJaquette(String jaquette) { this.jaquette = jaquette; }
}
