<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Barre de Navigation</title>
    <style>
        /* Style CSS pour la barre de navigation */
        .navbar {
            background-color: #333;
            overflow: hidden;
            padding: 14px 16px;
        }
        .navbar a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
            font-size: 17px;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .navbar a.active {
            background-color: #4CAF50;
            color: white;
        }

        /* Style CSS pour le formulaire */
        .form-container {
            max-width: 500px;
            margin: 20px auto;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .form-container h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .form-container input[type="text"],
        .form-container input[type="date"],
        .form-container input[type="number"],
        .form-container textarea {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }
        .form-container textarea {
            height: 100px;
            resize: vertical;
        }
        .form-container button {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
        }
        .form-container button:hover {
            background-color: #45a049;
        }
        .form-container input:required:invalid {
            border-color: #ff4444;
        }
        .form-container input:required:valid {
            border-color: #4CAF50;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <a href="home.jsp" class="<%= request.getRequestURI().endsWith("home.jsp") ? "active" : "" %>">Home</a>
        <a href="services.jsp" class="<%= request.getRequestURI().endsWith("services.jsp") ? "active" : "" %>">Services</a>
        <a href="projets.jsp" class="<%= request.getRequestURI().endsWith("projets") ? "active" : "" %>">Projets</a>
        <a href="login.jsp" class="<%= request.getRequestURI().endsWith("login.jsp") ? "active" : "" %>">Login</a>
    </div>

    <div class="form-container">
        <h2>Ajouter un Nouveau Projet</h2>
        <form action="projet" method="post">
            <input type="hidden" name="id_admin" value="1"> <!-- Remplacez 1 par la vraie valeur -->
            <input type="hidden" name="action" value="add"> <!-- Ajout de l'action -->
            <input type="text" name="nom" placeholder="Nom du projet" required>
            <textarea name="description" placeholder="Description du projet"></textarea>
            <input type="date" name="dateDebut" required>
            <input type="date" name="dateFin" required>
            <input type="number" step="0.01" name="budget" placeholder="Budget" required>
            <button type="submit">Ajouter Projet</button>
        </form>
    </div>
    
    
</body>
</html>