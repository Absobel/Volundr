<%@ page language="java" import="pack.*, java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
Utilisateur userSession = (Utilisateur) session.getAttribute("userSession");
%>



<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>

<body>

    Mes Evenements : <br>
    <br>
    <% Collection<Groupe> gs = (Collection<Groupe>) userSession.getGroupesU();
            for(Groupe g : gs) {
                if (!(g.getIsNotEventGroup())){
                    for( Evenement ev : g.getEvenements()){
                        String s = ev.getNom();
                        %>
                        <%= s %><br><br>
                        <a href="webscheduler/page_ajout_choix.html?user=<%= userSession.getMail()%>&id=<%= ev.getId()%>" id="pach"> Choix Créneau</a>                 
                        
                        <% } 
                    }
                } %>
</body>

</html>