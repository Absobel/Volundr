<%@ page language="java" import="pack.*, java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

  <head>
    <title>Volundr</title>
    <style>
@import url('https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap');

* {
  font-family: "Jost", sans-serif;
  letter-spacing: 0.05em;
  font-size: medium;
}

    body {
      height: 80vh;
      display: flex;
      flex-direction: column;
      justify-content: space-evenly;
    }

    button, p, article {
      display: block;
      margin: 0.5rem auto;
      margin-bottom: 0;
    }

    button, article {
      border: none;
      padding: 0.3rem 1rem;
      border-radius: 2rem;
    }

    form {
      text-align: center;
    }

    form article {
      background-color: #E9E9ED;
      display: inline-block;
    }

    button {
      background-color: #E9E9ED; 
    }

    article button {
      background-color: #CCF; 
    }

    button:hover {
      background-color: #AAF;
    }

    .closed > div {
      display: none;
    }

    article div {
      display: inline-block;
      text-align: center;
    }

    article div > * {
      display: inline-block;
    }
    </style>
  </head>

  <body>
    <%
    Utilisateur user =(Utilisateur) session.getAttribute("userSession"); 
    %>

    <% if (user != null) { %>

    <p>Bonjour <%= user.getPrenom() %></p>
    <p>Que voulez-vous faire aujourd'hui ?</p>

    <form action="Serv" method="post">

      <button type="submit" name="op" value="deconnexion">
        Deconnexion
      </button>
      <button type="submit" name="op" value="listeUserEvent">
        Mes Evenements
      </button>
      <article class="submenu">
        <header>Créer Événement</header>
        <div>
          <label for="eventname">Nom de l'événement :</label>
          <input type="text" id="eventname" name="eventname">
          <button type="button" id="creerEvent">✔️</button>
        </div>
      </article>
      <button type="submit" name="op" value="listeUserGroupe">
        Mes Groupes
      </button>
      <article class="submenu">
        <header>Créer Groupe</header>
        <div>
          <label for="groupname">Nom du groupe :</label>
          <input type="text" id="groupname" name="groupname">
          <button type="button" id="creerGroupe">✔️</button>
        </div>
      </article>
    </form>

    <% if (user.getIsAdmin()){ %>
    <p>Opérations d'administration ⚙️ :</p>
    <form action="Serv" method="post">
      <article class="submenu">
        <header>Creer Etablissement</header>
        <div>
          <label for="nomEtablissement">Nom de l'établissement :</label>
          <input type="text" name="nomEtablissement">
          <button type="submit" name="op" value="ajoutetablissement">✔️</button>
        </div>
      </article>
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
    <% }  %>

    <script>
Array.from(document.getElementsByClassName('submenu')).forEach(x => {
  x.classList.add("closed");
  x.getElementsByTagName('header')[0].addEventListener('click', () => {
    x.style.display = (x.classList.contains("closed")) ? x.classList.remove("closed") : x.classList.add("closed");
  });
});

  document.getElementById('creerGroupe').addEventListener('click', () => {

    fetch("http://localhost:8080/Volundr/rest/tutorial/creerGroupeVide", {
      method: "post",

      // Make sure to serialize your JSON body
      body: document.getElementById("groupname").value

    }).then(async (response) => {
      // Do something awesome that makes the world a better place
      window.location = `http://localhost:8080/Volundr/update_groupe.html?id=\${(await response.json()).id}`;
    });
  });

  document.getElementById('creerEvent').addEventListener('click', () => {
    // Appel à l'api pour ajouter une personne à la BDD
    fetch("http://localhost:8080/Volundr/rest/tutorial/creerEvent", {
      method: "post",
      // Make sure to serialize your JSON body
      body: document.getElementById("eventname").value

    }).then(async (response) => {
      // Do something awesome that makes the world a better place
      window.location = `http://localhost:8080/Volundr/update_group_event.html?id=\${(await response.json()).id}`;
    });
  });

    </script>
  </body>
</html>
