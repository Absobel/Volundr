<%@ include file="header.jsp" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ page import="model.*" %>
            <%@ page import="java.util.*" %>
                <% Utilisateur userSession=(Utilisateur) session.getAttribute("userSession"); %>
                    <!DOCTYPE html>
                    <html>

                    <head>
                        <meta charset="UTF-8">
                        <title>Volundr</title>
                        <link rel="stylesheet" type="text/css" href="style.css">
                    </head>

                    <body>
                        <div class="notheader">
                            Liste des evenements : <br>
                            <br>
                            <% Collection<Evenement> es = (Collection<Evenement>)
                                    request.getAttribute("listeevenements");
                                    for(Evenement e : es) {

                                    String s = e.getNom() + " " ;

                                    %>
                                    <p class="nomevent">
                                    <%= s %>
                                    </p>
                                        <% if(userSession.getIsAdmin()){ %>
                                            <a href="eventManager.jsp?id=<%= e.getId()%>" id=<%=e.getId()%> >Setting
                                                Event</a>
                                            <%}%> <br>
                                                <% } %>
                                <a href="index.jsp" id="retourAcceuil" /> Retour</a><br>
                        </div>

                    </body>
        
                    <script>

                    </script>

                    </html>