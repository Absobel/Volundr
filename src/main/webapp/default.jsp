<%@ page language="java" import="pack.*, java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Volundr</title>
    <!-- link href="css/style.css" rel="stylesheet"> -->
</head>
<body>   
    <a href="https://sd-160040.dedibox.fr/hagimont/" target="_blank">Chez le boss</a>
    <h1>Hello there.</h1>
    <h1> GENERAL KENOBI</h1>
    <img src="4h8g6f.jpg" alt="Description de l'image"><br>
</body>

<a href="creer_event.html" target="_blank">Chez le boss</a>

<form action="Serv" method="post">
    <div class="rectangle">
        <div class="header">Événement</div>
        <button type="submit" name="op" value="creerEvenement">
            Créer 
        </button>
    </div>
</form>

<% Utilisateur user = (Utilisateur) request.getAttribute("userSession");
    if (user.getMail().equals("olivierblot@gmail.com")){
        %> 
        <form action="Serv" method="post">
            <button type="submit" name="op" value="creeretablissement">
                Creer Etablissement
            </button>
            <button type="submit" name="op" value="listeetablissements">
                Liste Etablissements
            </button>
            <button type="submit" name="op" value="listeutilisateurs">
                Liste utilisateur
            </button>
        </form>  
        <%
    }
 %>

</html>


