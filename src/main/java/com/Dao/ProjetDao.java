package com.Dao;

import com.models.Projet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetDao {
    private Connection connection;

    // Constructeur prenant une connexion à la base de données
    public ProjetDao(Connection connection) {
        this.connection = connection;
    }

    // 1. Créer un nouveau projet
    public void creerProjet(Projet projet) throws SQLException {
        String query = "INSERT INTO projet (id_admin, nom, description, date_debut, date_fin, budget) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, 1); // Tous les projets auront id_admin = 1
            stmt.setString(2, projet.getNom());
            stmt.setString(3, projet.getDescription());
            stmt.setDate(4, projet.getDateDebut());
            stmt.setDate(5, projet.getDateFin());
            stmt.setDouble(6, projet.getBudget());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création du projet, aucune ligne affectée.");
            }
        }
    }


    public List<Projet> getListeProjets() throws SQLException {
        List<Projet> projets = new ArrayList<>();
        String query = "SELECT id_projet, nom, description, date_debut, date_fin, budget FROM projet";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_projet");  // 🔥 Récupération de l'ID
                String nom = resultSet.getString("nom");
                String description = resultSet.getString("description");
                Date dateDebut = resultSet.getDate("date_debut");
                Date dateFin = resultSet.getDate("date_fin");
                double budget = resultSet.getDouble("budget");

                // 🔥 Associer l'ID au projet
                Projet projet = new Projet(id, nom, description, dateDebut, dateFin, budget);
                projets.add(projet);
            }
        }
        return projets;
    }




    // 3. Mettre à jour les détails d'un projet existant
    public void mettreAJourProjet(Projet projet) throws SQLException {
        String query = "UPDATE projet SET nom = ?, description = ?, date_debut = ?, date_fin = ?, budget = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, projet.getNom());
            stmt.setString(2, projet.getDescription());
            stmt.setDate(3, projet.getDateDebut());
            stmt.setDate(4, projet.getDateFin());
            stmt.setDouble(5, projet.getBudget());
            stmt.setInt(6, projet.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la mise à jour, aucun projet trouvé avec l'ID " + projet.getId());
            }
        }
    }


    // 4. Supprimer un projet existant
    public void supprimerProjet(int id) throws SQLException {
        String query = "DELETE  FROM projet WHERE id_projet = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la suppression, aucun projet trouvé avec l'ID " + id);
            }
        }
    }
    
    public Projet getProjetById(int projetId) throws SQLException {
        String query = "SELECT * FROM projet WHERE id_projet = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, projetId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                Date dateDebut = rs.getDate("date_debut");
                Date dateFin = rs.getDate("date_fin");
                double budget = rs.getDouble("budget");
                
                return new Projet(projetId, nom, description, dateDebut, dateFin, budget);
            }
        }
        return null; // Retourne null si aucun projet trouvé
    }

}
