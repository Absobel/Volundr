<%@ page language="java" import="model.*, java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ include file="header.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>update group</title>
      <link rel="stylesheet" type="text/css" href="style.css">
    </head>

    <body>

      <main>
        <form>
          <label for="email">Choisir un email :</label>
          <select name="cars" id="email">
          </select>
        </form>

        <a href="#add" id="add">add user to group</a>
        <a href="#del" id="del">del user to group</a>

        <form>
          <label for="group">Choisir un groupe :</label>
          <select name="groupe" id="groupe">
          </select>
        </form>

        <a href="#addG" id="addG">add users of the group</a>
        <a href="#delG" id="delG">del users of the group</a>

        <textarea id="affichage" rows="5" cols="33" readonly>
        petit texte de test
      </textarea>

        <a href="index.jsp" id="valider">valider</a>
      </main>

    </body>

    <script>
      /* l'id de l'event qu'on modifie */
      const id = new URLSearchParams(window.location.search).get('id');

      /* l'objet html pour selectionner un email */
      const emailSelector = document.getElementById('email');

      const groupSelector = document.getElementById('groupe');

      /* l'objet html pour visualiser le groupe */
      const affichageTextarea = document.getElementById('affichage');

      function updateAffichage() {
        // Récupérer la liste des utilisateurs du groupe associé à l'event
        fetch(`/rest/tutorial/getUsersEvent/\${id}`)
          .then(response => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.json();
          })
          .then(data => {
            affichageTextarea.innerText = data.reduce((accumulator, currentValue) => accumulator + "--" + currentValue, "")
          })

        // Récupérer la liste de tous les utilisateurs de la base de donnée pour pouvoir les selectionner
        fetch(`/rest/tutorial/getUsers`)
          .then(response => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.json();
          })
          .then(data => {
            emailSelector.innerHTML = "";
            data.map(currentValue => {
              let newOption = document.createElement('option');
              newOption.setAttribute('value', currentValue.mail);
              newOption.innerText = currentValue.mail;
              emailSelector.appendChild(newOption);
            })
          })

        fetch(`/rest/tutorial/getGroups`)
          .then(response => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.json();
          })
          .then(data => {
            groupSelector.innerHTML = "";
            data.map(currentValue => {
              let newOption = document.createElement('option');
              newOption.setAttribute('value', currentValue.id);
              newOption.innerText = currentValue.nom;
              groupSelector.appendChild(newOption);
            })
          })

      }

      document.getElementById("add").addEventListener("click", () => {

        // Appel à l'api pour ajouter une personne à la BDD
        fetch(`/rest/tutorial/addUserEvent/\${id}`, {
          method: "post",
          body: emailSelector.value

        }).then((response) => {
          //do something awesome that makes the world a better place
          console.log(response)

          // Appel à l'api pour update l'affichage
          updateAffichage();
        });
      });

      document.getElementById("del").addEventListener("click", () => {

        // Appel à l'api pour ajouter une personne à la BDD
        fetch(`/rest/tutorial/delUserEvent/\${id}`, {
          method: "post",
          body: emailSelector.value

        }).then((response) => {
          //do something awesome that makes the world a better place
          console.log(response)

          // Appel à l'api pour update l'affichage
          updateAffichage();
        });
      });

      document.getElementById("addG").addEventListener("click", () => {

        // Appel à l'api pour ajouter une personne à la BDD
        fetch(`/rest/tutorial/addUserFromGroupEvent/\${id}`, {
          method: "post",
          body: groupSelector.value

        }).then((response) => {
          //do something awesome that makes the world a better place
          console.log(response)

          // Appel à l'api pour update l'affichage
          updateAffichage();
        });
      });

      document.getElementById("delG").addEventListener("click", () => {

        // Appel à l'api pour ajouter une personne à la BDD
        fetch(`/rest/tutorial/delUsersFromGroupEvent/\${id}`, {
          method: "post",
          body: groupSelector.value

        }).then((response) => {
          //do something awesome that makes the world a better place
          console.log(response)

          // Appel à l'api pour update l'affichage
          updateAffichage();
        });
      });
      // actualiser l'affichage au chargement de la page
      window.onload = updateAffichage;
    </script>

    </html>