package com.servlet;

import com.Dao.ProjetDao;
import com.models.Projet;
import com.utils.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


public class ProjetServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProjetDao projetDao;

    @Override
    public void init() throws ServletException {
        Connection connection = DatabaseConnection.getConnection();
        projetDao = new ProjetDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Projet> projets = projetDao.getListeProjets();
            request.setAttribute("projets", projets);
            request.getRequestDispatcher("projets.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
        	 String nom = request.getParameter("nom");
             String description = request.getParameter("description");
             Date dateDebut = Date.valueOf(request.getParameter("dateDebut"));
             Date dateFin = Date.valueOf(request.getParameter("dateFin"));
             double budget = Double.parseDouble(request.getParameter("budget"));
             
            if ("add".equals(action)) {
               
                projetDao.creerProjet(nom, description, dateDebut, dateFin, budget);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                projetDao.supprimerProjet(id);
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                
                projetDao.mettreAJourProjet(id, nom, description, dateDebut, dateFin, budget);
            }
            response.sendRedirect("projet");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
