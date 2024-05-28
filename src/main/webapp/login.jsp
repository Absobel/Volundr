<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Volundr login</title>
    <style>
@import url('https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap');

* {
  font-family: "Jost", sans-serif;
  letter-spacing: 0.05em;
}

    body {
      height: 80vh;
      display: flex;
      flex-direction: column;
      justify-content: center;
      position: relative;
    }

    .spacer {
      flex-grow: 1;
    }

    button, p, div, strong {
      margin: 0.5rem auto;
    }

    strong {
      color: red;
    }

    div {
      display: grid;
      grid-auto-columns: 1fr 1fr; 
      grid-auto-rows: 1fr; 
      grid-template-columns: 1fr 2fr; 
      grid-template-rows: 1fr 1fr;
      gap: 20px;
    }

    button {
      border: none;
      padding: 0.3rem 1rem;
      border-radius: 2rem;
    }

    form {
      display: inline-block;
      margin: 0.5rem auto;
    }
    </style>
  </head>
  <body>
    <p>Bienvenue sur Volundr</p>
    <p>Merci de vous connecter</p>

    <% if (request.getAttribute("fail") != null) { %>
    <strong>Mauvais utilisateur/ Mot de passe. Veuillez réessayer.</strong>
    <% } %>

    <form action="Serv" method="post">
      <div>
        <label for="mail">Mail :</label> <input type="text" name="mail" id="mail" required>
        <label for="password">Password :</label> <input type="password" name="password" id="password" required>
      </div>
      <button type="submit" name="op" value="chargerutilisateur">
        Connexion
      </button>
      <button type="submit" name="op" value="creerutilisateur" formnovalidate>
        Inscription
      </button>
    </form>
  </body>
</html>
