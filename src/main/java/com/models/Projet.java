package com.models;

import java.sql.Date;

public class Projet {
	private int id;
    private String nom;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private double budget;
    
    public Projet() {}

    // Constructeur
    public Projet(int id,String nom, String description, Date dateDebut, Date dateFin, double budget) {
    	this.id=id;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.budget = budget;
    }
    
    public Projet(String nom, String description, Date dateDebut, Date dateFin, double budget) {
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.budget = budget;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public double getBudget() {
        return budget;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "Projet{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", budget=" + budget +
                '}';
    }


   
}