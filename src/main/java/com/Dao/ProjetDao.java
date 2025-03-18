package com.Dao;

import com.models.Projet;
import com.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetDao {
    private Connection connection;

    // Constructeur prenant une connexion à la base de données
    public ProjetDao() {
        this.connection = DatabaseConnection.getConnection();
    }

    // 1. Créer un nouveau projet
    public void creerProjet(String nom, String description, Date dateDebut, Date dateFin, double budget) throws SQLException {
        String query = "INSERT INTO projet (nom, description, date_debut, date_fin, budget) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nom);
            stmt.setString(2, description);
            stmt.setDate(3, dateDebut);
            stmt.setDate(4, dateFin);
            stmt.setDouble(5, budget);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création du projet, aucune ligne affectée.");
            }
        }
    }

    // 2. Afficher la liste des projets existants avec leurs détails
    public List<Projet> getListeProjets() throws SQLException {
        List<Projet> projets = new ArrayList<>();
        String query = "SELECT * FROM projet";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Projet projet = new Projet(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getDouble("budget")
                );
                projets.add(projet);
            }
        }
        return projets;
    }

    // 3. Mettre à jour les détails d'un projet existant
    public void mettreAJourProjet(int id, String nom, String description, Date dateDebut, Date dateFin, double budget) throws SQLException {
        String query = "UPDATE projet SET nom = ?, description = ?, date_debut = ?, date_fin = ?, budget = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, description);
            stmt.setDate(3, dateDebut);
            stmt.setDate(4, dateFin);
            stmt.setDouble(5, budget);
            stmt.setInt(6, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la mise à jour, aucun projet trouvé avec l'ID " + id);
            }
        }
    }

    // 4. Supprimer un projet existant
    public void supprimerProjet(int id) throws SQLException {
        String query = "DELETE FROM projet WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la suppression, aucun projet trouvé avec l'ID " + id);
            }
        }
    }
}
