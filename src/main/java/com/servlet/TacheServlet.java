package com.servlet;

import com.Dao.TacheDao;
import com.Dao.ProjetDao;
import com.models.Tache;
import com.models.Projet;
import com.utils.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/tache")
public class TacheServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TacheDao tacheDao;
    private ProjetDao projetDao;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        connection = DatabaseConnection.getConnection();
        if (connection == null) {
            throw new ServletException("Impossible d'obtenir une connexion à la base de données.");
        }
        tacheDao = new TacheDao(connection);
        projetDao = new ProjetDao(connection);
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("delete".equals(action)) {
                supprimerTache(request, response);
                return; // Stoppe l'exécution après la suppression
            }

            // Affichage des tâches associées à un projet
            afficherTaches(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors du traitement : " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                ajouterTache(request, response);
                return;
            } else if ("update".equals(action)) {
                modifierTache(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
            
            if (!response.isCommitted()) { // Vérifie si la réponse est encore modifiable
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }

    // Méthodes spécifiques pour gérer les tâches

    private void supprimerTache(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int projetId = Integer.parseInt(request.getParameter("projetId"));

        tacheDao.supprimerTache(id);
        request.getSession().setAttribute("successMessage", "Tâche supprimée avec succès !");
        response.sendRedirect("tache?projetId=" + projetId);
    }

    private void afficherTaches(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int projetId = Integer.parseInt(request.getParameter("projetId"));
        List<Tache> taches = tacheDao.getTachesParProjet(projetId);
        Projet projet = projetDao.getProjetById(projetId);

        request.setAttribute("taches", taches);
        request.setAttribute("projet", projet);
        request.getRequestDispatcher("taches.jsp").forward(request, response);
    }

    private void ajouterTache(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int projetId = Integer.parseInt(request.getParameter("projetId"));
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        Date dateDebut = Date.valueOf(request.getParameter("dateDebut"));
        Date dateFin = Date.valueOf(request.getParameter("dateFin"));

        Tache tache = new Tache(0, nom, description, dateDebut, dateFin, projetId);
        tacheDao.ajouterTache(tache);

        request.getSession().setAttribute("successMessage", "Tâche ajoutée avec succès !");
        response.sendRedirect("tache?projetId=" + projetId);
    }

    private void modifierTache(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int projetId = Integer.parseInt(request.getParameter("projetId"));
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        Date dateDebut = Date.valueOf(request.getParameter("dateDebut"));
        Date dateFin = Date.valueOf(request.getParameter("dateFin"));

        Tache tache = new Tache(id, nom, description, dateDebut, dateFin, projetId);
        tacheDao.mettreAJourTache(tache);

        request.getSession().setAttribute("successMessage", "Tâche mise à jour avec succès !");
        response.sendRedirect("tache?projetId=" + projetId);
    }
}
