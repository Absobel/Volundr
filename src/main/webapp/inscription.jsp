<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Volundr register</title>
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
  grid-template-rows: 1fr 1fr 1fr 1fr 1fr 1fr;
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

    <p>Pour vous inscrire, remplissez les champs suivants</p>

    <%
    Integer fail = (Integer) request.getAttribute("fail");
    if (fail != null) {
    if (fail == 1) {
    %>
    <strong>Veuillez confirmer l'adresse/le mot de passe . Veuillez réessayer.</strong>
    <% } else { %>
    <strong>Ce mail est déjà utilisé . Veuillez réessayer.</strong>
    <% }
    } %>

    <form action="Serv" method="post">
      <div>
        <label for="nom">Nom :</label> <input type="text" name="nom" id="nom" required>
        <label for="prenom">Prenom :</label> <input type="text" name="prenom" id="prenom" required>
        <label for="newmail">Mail :</label> <input type="text" name="newmail" id="newmail" required>
        <label for="cmail"> Confirmer Mail :</label> <input type="text" name="cmail" id="cmail" required>
        <label for="newpassword">Mot de passe :</label> <input type="password" name="newpassword" id="newpassword"
          required>
        <label for="cword">Confirmer Mot de passe :</label> <input type="password" name="cword" id="cword" required>
      </div>
      <button type="submit" name="op" value="inscrireutilisateur">
        Valider
      </button>
      <button type="submit" name="op" value="retournerarriere" formnovalidate>
        Retour
      </button>
    </form>

  </body>
</html>
