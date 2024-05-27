<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>event manager</title>
  <!-- <link href="css/style.css" rel="stylesheet"> -->
</head>

<body>
    <main>
      
      <% String id = request.getParameter("id"); %>
        <a href="update_group_event.html?id=<%= id %>" id="uge">Modifier Groupe Evenement</a>
        <a href="webscheduler/page_ajout_creneaux.html?id=<%= id %>" id="pac">Ajouter Creneaux</a>
    </main>
</body>

</html>