<%@ include file="header.jsp" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ page import="model.*" %>
            <%@ page import="java.util.*" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>Volundr</title>
                    <link rel="stylesheet" type="text/css" href="style.css">
                </head>

                <body>
                    <div class="notheader">

                        Liste des groupes : <br>
                        <br>
                        <% Collection<Groupe> gs = (Collection<Groupe>) request.getAttribute("listegroupes");
                                for(Groupe g : gs) {
                                if ( g.getIsNotEventGroup()){


                                String s = g.getNom();

                                %>
                                <p class="nomevent">
                                <%= s %> 
                                </p><br>
                                    <blockquote>
                                        <p>
                                            <% for (Utilisateur u : g.getUtilisateurs()){ String userName=u.getPrenom()
                                                + " " + u.getNom(); %>
                                                <%= userName %> <br>
                                                    <% } %>
                                        </p>
                                    </blockquote>
                                    <% } } %>
                                        <a href="index.jsp" id="retourAcceuil" /> Retour</a><br>
                    </div>
                </body>

                </html>