<%@ include file="header.jsp" %>
    <%@ page language="java" import="model.*, java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <% Utilisateur userSession=(Utilisateur) session.getAttribute("userSession"); %>

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Volundr</title>
                <link rel="stylesheet" type="text/css" href="style.css">

            </head>

            <body>

                <p>Mes groupes : </p><br>
                <br>
                <% Collection<Groupe> gs = (Collection<Groupe>) userSession.getGroupesU();
                        for(Groupe g : gs) {
                        if ( g.getIsNotEventGroup()){


                        String s = g.getNom();

                        %>
                        <p class="nomevent">
                            <%= s %>
                        </p> <br>
                        <blockquote>
                            <p>
                                <% for (Utilisateur u : g.getUtilisateurs()){ String userName=u.getPrenom() + " " +
                                    u.getNom(); %>
                                    <%= userName %> <br>
                                        <% } %>
                            </p>
                        </blockquote>
                        <% } } %>

                            <a href="index.jsp" id="retourAcceuil" /> Retour</a><br>
            </body>

            </html>