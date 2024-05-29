<%@ include file="header.jsp" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ page import="pack.*" %>
            <%@ page import="java.util.*" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>Insert title here</title>
                    <link rel="stylesheet" type="text/css" href="style.css">
                </head>

                <body>

                    <p>Liste des Utilisateurs :</p> <br>
                    <br>
                    <p>
                        <% Collection<Utilisateur> us = (Collection<Utilisateur>)
                                request.getAttribute("listeutilisateurs");
                                for(Utilisateur u : us) {

                                String s = u.getPrenom() + " " + u.getNom() ;

                                %>
                                <%= s %> <br>
                                    <% } %>
                                        <a href="index.jsp" id="retourAcceuil" /> Retour</a><br>
                    </p>
                </body>

                </html>