<%@ page language="java" import="pack.*, java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>Volundr</title>
        <!-- link href="css/style.css" rel="stylesheet"> -->
    </head>

    <body>
        
        <% int sessionTime = session.getMaxInactiveInterval(); 
       %>
        <form action="Serv" method="post">
               
                <button type="submit" name="op" value="deconnexion">
                    Deconnexion
                </button>
        </form>
        <%= sessionTime %> <br>
        <a href="https://sd-160040.dedibox.fr/hagimont/" target="_blank">Chez le boss</a>
        <h1>Hello there.</h1>
        <h1> GENERAL KENOBI</h1>
        <img src="4h8g6f.jpg" alt="Description de l'image"><br>
    </body>

    <a href="creer_event.html" target="_blank">Chez le boss</a>

    <form action="Serv" method="post">
        <div class="rectangle">
            <div class="header">Événement</div>
            <button type="submit" name="op" value="listeUserEvent">
                Mes Evenements
            </button>
            <button type="submit" name="op" value="creerEvenement">
                Créer Événement
            </button>
        </div>
    </form>

    <form action="Serv" method="post">
        <div class="rectangle">
            <div class="header">Groupe</div>
            <button type="submit" name="op" value="listeUserGroupe">
                Mes Groupes
            </button>
            <button type="submit" name="op" value="creerGroupe">
                Créer Groupe
            </button>
        </div>
    </form>

    <%  
        Utilisateur user =(Utilisateur) session.getAttribute("userSession"); 


        if (user.getIsAdmin()){ %>
        <form action="Serv" method="post">
            <button type="submit" name="op" value="creeretablissement">
                Creer Etablissement
            </button>
            <button type="submit" name="op" value="listeetablissements">
                Liste Etablissements
            </button>
            <button type="submit" name="op" value="listeutilisateurs">
                Liste Utilisateurs
            </button>
            <button type="submit" name="op" value="listeevenements">
                Liste Evenements
            </button>
            <button type="submit" name="op" value="listegroupes">
                Liste Groupes
            </button>
            <button type="submit" name="op" value="creersalle">
                Creer Salle
            </button>
            <button type="submit" name="op" value="listersalles">
                Liste Salles
            </button>
        </form>
        <% }  %>

    </html>