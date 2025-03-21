<%@ page import="java.util.List" %>
<%@ page import="com.models.Projet" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des projets</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .card-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
            margin-top: 20px;
        }
        .project-card {
            width: 300px;
            border: 1px solid #ddd;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s ease-in-out;
            background: white;
        }
        .project-card:hover {
            transform: scale(1.05);
        }
        .card-body {
            padding: 15px;
        }
        .card-title {
            font-size: 18px;
            font-weight: bold;
            color: #007bff;
        }
        .card-text {
            font-size: 14px;
            color: #555;
        }
        .delete-btn {
            color: red;
            font-weight: bold;
            text-decoration: none;
        }
        .delete-btn:hover {
            color: darkred;
        }
        .success-message {
            color: green;
            padding: 10px;
            background-color: #DFF2BF;
            border: 1px solid green;
            margin-bottom: 10px;
            text-align: center;
            border-radius: 5px;
        }
    </style>
</head>
<body class="container">

    <% String successMessage = (String) session.getAttribute("successMessage");
    if (successMessage != null) {
        session.removeAttribute("successMessage");
    %>
        <div class="success-message"><%= successMessage %></div>
    <% } %>

    <h2 class="text-center mt-4">Liste des projets</h2>
    <div class="text-center mb-3">
        <a href="ajouterProjet.jsp" class="btn btn-primary">Ajouter un projet</a>
    </div>

    <%
        List<Projet> projets = (List<Projet>) request.getAttribute("projets");
        if (projets == null || projets.isEmpty()) {
    %>
        <p class="text-center text-danger">Aucun projet trouvé.</p>
    <%
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    %>
        <div class="card-container">
            <%
                for (Projet p : projets) {
            %>
            <div class="project-card">
                <div class="card-body">
                    <h5 class="card-title"><%= p.getNom() %></h5>
                    <p class="card-text"><strong>Description :</strong> <%= p.getDescription() %></p>
                    <p class="card-text"><strong>Date début :</strong> <%= sdf.format(p.getDateDebut()) %></p>
                    <p class="card-text"><strong>Date fin :</strong> <%= sdf.format(p.getDateFin()) %></p>
                    <p class="card-text"><strong>Budget :</strong> <%= String.format("%.2f", p.getBudget()) %> €</p>
                    <div class="text-center">
                        <a href="projet?action=delete&id=<%= p.getId() %>" 
                           class="delete-btn"
                           onclick="return confirm('Supprimer ce projet ?');">Supprimer</a>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
    <%
        }
    %>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
