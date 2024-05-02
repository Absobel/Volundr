<%@ page language="java" import="pack.*, java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Volundr</title>
</head>
<body>   
    <a href="https://sd-160040.dedibox.fr/hagimont/" target="_blank">Chez le boss</a>
    <h1>Hello there.</h1>
    <h1> GENERAL KENOBI</h1>
    <img src="4h8g6f.jpg" alt="Description de l'image"><br>
</body>

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


