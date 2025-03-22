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

}