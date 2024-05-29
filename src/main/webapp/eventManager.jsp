<%@ page language="java" import="model.*, java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ include file="header.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>event manager</title>
      <link rel="stylesheet" type="text/css" href="style.css">
    </head>

    <body>
      <main>

        <% String id=request.getParameter("id"); %>
          <a href="update_group_event.html?id=<%= id %>" id="uge">Modifier Groupe Evenement</a>
          <a href="webscheduler/page_ajout_creneaux.html?id=<%= id %>" id="pac">Ajouter Creneaux</a>
      </main>
    </body>
    <a href="index.jsp" id="retourAcceuil" /> Retour Menu</a> <br>

    </html>