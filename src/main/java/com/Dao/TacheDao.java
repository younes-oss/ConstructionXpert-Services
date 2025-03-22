package com.Dao;

import com.models.Tache;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacheDao {

	private Connection connection;


public TacheDao(Connection connection) {
	this.connection=connection;
}

// Ajouter une tâche à un projet existant
		public void ajouterTache(Tache tache) throws SQLException {
		    String query = "INSERT INTO tache (nom, description, statut, date_debut, date_fin, projet_id) VALUES (?, ?, ?, ?, ?, ?)";
		    try (PreparedStatement stmt = connection.prepareStatement(query)) {
		        stmt.setString(1, tache.getNom());
		        stmt.setString(2, tache.getDescription());
		        stmt.setDate(3, new java.sql.Date(tache.getDateDebut().getTime()));
		        stmt.setDate(4, new java.sql.Date(tache.getDateFin().getTime()));
		        stmt.setInt(5, tache.getProjetId());
		        stmt.executeUpdate();
		    }
		}

		
		 // Récupérer la liste des tâches associées à un projet
	    public List<Tache> getTachesParProjet(int projetId) throws SQLException {
	        List<Tache> taches = new ArrayList<>();
	        String query = "SELECT * FROM tache WHERE projet_id = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setInt(1, projetId);
	            ResultSet resultSet = stmt.executeQuery();

	            while (resultSet.next()) {
	                Tache tache = new Tache(
	                    resultSet.getInt("id"),
	                    resultSet.getString("nom"),
	                    resultSet.getString("description"),
	                    resultSet.getDate("date_debut"),
	                    resultSet.getDate("date_fin"),
	                    resultSet.getInt("projet_id")
	                );
	                taches.add(tache);
	            }
	        }
	        return taches;
	    }
	    
	    public void mettreAJourTache(Tache tache) throws SQLException {
	        String query = "UPDATE tache SET nom = ?, description = ?, statut = ?, date_debut = ?, date_fin = ? WHERE id = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setString(1, tache.getNom());
	            stmt.setString(2, tache.getDescription());
	            stmt.setDate(3, new java.sql.Date(tache.getDateDebut().getTime()));
	            stmt.setDate(4, new java.sql.Date(tache.getDateFin().getTime()));
	            stmt.setInt(5, tache.getId());
	            stmt.executeUpdate();
	        }
	    }
	    
	    
}