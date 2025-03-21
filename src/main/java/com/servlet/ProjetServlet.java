package com.servlet;

import com.Dao.ProjetDao;
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

@WebServlet("/projet")  // Correction du mapping
public class ProjetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProjetDao projetDao;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        connection = DatabaseConnection.getConnection();
        if (connection == null) {
            throw new ServletException("Impossible d'obtenir une connexion à la base de données.");
        }
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
                String idParam = request.getParameter("id");
                System.out.println("ID reçu pour suppression : " + idParam); // Debug
                
                if (idParam == null || idParam.isEmpty()) {
                    System.out.println("Erreur : ID invalide !");
                    response.sendRedirect("projets.jsp"); // Rediriger pour éviter une erreur
                    return;
                }

                int id = Integer.parseInt(idParam);
                System.out.println("Tentative de suppression du projet avec ID: " + id);

                projetDao.supprimerProjet(id);
                request.getSession().setAttribute("successMessage", "Projet supprimé avec succès !");
                
                // ✅ Redirection après suppression pour éviter IllegalStateException
                response.sendRedirect("projet");
                return; // 🔥 Très important : stoppe l'exécution ici
            }

            // Affichage des projets (si ce n'est pas une suppression)
            List<Projet> projets = projetDao.getListeProjets();
            request.setAttribute("projets", projets);
            request.getRequestDispatcher("projets.jsp").forward(request, response);

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
                String nom = request.getParameter("nom");
                String description = request.getParameter("description");
                String dateDebutStr = request.getParameter("dateDebut");
                String dateFinStr = request.getParameter("dateFin");
                String budgetStr = request.getParameter("budget");

                if (nom == null || description == null || dateDebutStr == null || dateFinStr == null || budgetStr == null) {
                    throw new IllegalArgumentException("Tous les champs sont obligatoires !");
                }

                Date dateDebut = Date.valueOf(dateDebutStr);
                Date dateFin = Date.valueOf(dateFinStr);
                double budget = Double.parseDouble(budgetStr);

                Projet projet = new Projet(nom, description, dateDebut, dateFin, budget);
                projetDao.creerProjet(projet);

                request.getSession().setAttribute("successMessage", "Projet ajouté avec succès !");
                response.sendRedirect("projet");

                return; // Empêcher tout autre traitement après la redirection
            } 
            else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nom = request.getParameter("nom");
                String description = request.getParameter("description");
                Date dateDebut = Date.valueOf(request.getParameter("dateDebut"));
                Date dateFin = Date.valueOf(request.getParameter("dateFin"));
                double budget = Double.parseDouble(request.getParameter("budget"));

                Projet projet = new Projet(id, nom, description, dateDebut, dateFin, budget);
                projetDao.mettreAJourProjet(projet);
                response.sendRedirect("projets");
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

    
}
