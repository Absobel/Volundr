<%@ page language="java" import="model.*, java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="header.jsp" %>
        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>lister salles</title>
            <link rel="stylesheet" type="text/css" href="style.css">

        </head>

        <body>
            <div id="listersalles"></div>
            <div id="result"></div>
            <a href="index.jsp" id="retourAcceuil" /> Retour</a><br>
        </body>

        <script>
            fetch("/rest/tutorial/getEtablissements", {
                method: "get"
            }).then(async (response) => {
                const etablissements = await response.json();
                const etablissementSelect = document.createElement("select");
                etablissements.forEach((etablissement) => {
                    const option = document.createElement("option");
                    option.value = etablissement.id;
                    option.text = etablissement.nom;
                    etablissementSelect.appendChild(option);
                });

                const button = document.createElement("button");
                button.textContent = "Validate";
                button.addEventListener("click", () => {
                    const resultDiv = document.getElementById("result");
                    resultDiv.innerHTML = ""; // Clear the result div before populating it with new data

                    const selectedEtablissementId = etablissementSelect.value;
                    // Make your request here using the selectedEtablissementId
                    const salles = etablissements.find((etablissement) => etablissement.id == selectedEtablissementId).salles;
                    const listersalles = document.createElement("p");
                    salles.forEach((salle, index) => {
                        listersalles.textContent += salle.batiment + salle.numero;
                        if (index !== salles.length - 1) {
                            listersalles.textContent += ", ";
                        }
                    });
                    document.getElementById("result").appendChild(listersalles);
                });

                document.getElementById("listersalles").appendChild(etablissementSelect);
                document.getElementById("listersalles").appendChild(button);
            });

        </script>

        </html>