import Scheduler from "./Scheduler.js";
import Room from "./Room.js";
import Creneau from "./Creneau.js";

customElements.define('volundr-scheduler', Scheduler);
customElements.define('volundr-room', Room);
customElements.define('volundr-creneau', Creneau);

/* l'id de l'event qu'on modifie */
const id = new URLSearchParams(window.location.search).get('id');

const scheduler = document.getElementById('scheduler');
const addCreneau = document.getElementById('add-creneau');
const choixDuree = document.getElementById('duree-creneau');
const choixSalle = document.getElementById('choix-salle');
const addSalle = document.getElementById('add-salle');
const runAfectation = document.getElementById('run-affectation');


/** Fonction pour ajouter une salle dans le scheduler si elle n'y est pas déjà */
function addSalleScheduler(idSalle, nomSalle) {
  let idAttr = `volundr-room-${idSalle}`;

  /* on crée la room seulement si elle n'est pas déjà présente */
  if (document.getElementById(idAttr) === null) {
    let new_room = document.createElement('volundr-room');
    new_room.setAttribute('data-name', nomSalle);
    new_room.setAttribute('id', idAttr);
    new_room.setAttribute('data-id', idSalle);
    new_room.setAttribute('data-time-start', "8h");
    new_room.setAttribute('data-time-end', "18h");
    scheduler.appendChild(new_room);
  }
}


function updateAffichage() {
  // Récupérer la liste de toutes les salles de la base de donnée pour pouvoir les selectionner
  fetch(`http://localhost:8080/Volundr/rest/tutorial/getSalles`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      choixSalle.innerHTML = "";
      data.map(currentValue => {
        let newOption = document.createElement('option');
        newOption.setAttribute('value', currentValue.id);
        newOption.innerText = `${currentValue.batiment}.${currentValue.numero}`;
        choixSalle.appendChild(newOption);
      })
    })


  // Récupérer la liste des créneaux de l'event et les ajouter dans le scheduler
  fetch(`http://localhost:8080/Volundr/rest/tutorial/getCasesEvent/${id}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      /* on supprime tous les anciens créneaux */
      Array.from(scheduler.getElementsByTagName('volundr-creneau')).forEach(e => e.remove());

      /* on ajoute tous les créneaux obtenus depuis le serveur */
      data.map(creneau => {
        /* on s'assure que la salle existe */
        addSalleScheduler(creneau.salleC.id,
          `${creneau.salleC.batiment}.${creneau.salleC.numero}`);

        /* on crée l'objet 'volundr-creneau' et on l'ajoute au scheduler */
        let new_creneau = new Creneau();
        new_creneau.setAttribute('data-time', creneau.debutCreneau);
        new_creneau.setAttribute('data-duration',
          creneau.finCreneau - creneau.debutCreneau);

        /* important de récupérer l'id sinon quand on va rappeler le serveur
         * on va créer une copie au lieu de modifier l'original */
        new_creneau.setAttribute('data-id', creneau.id);

        /* afficher la personne à qui on a affecté le créneau
         * (si l'affectation a déjà eu lieu) */
        new_creneau.innerText = (creneau.usagerChoisi === undefined) ? "" : `${creneau.usagerChoisi.prenom} ${creneau.usagerChoisi.nom}`;

        document.getElementById(`volundr-room-${creneau.salleC.id}`).appendChild(new_creneau);


        /* ON AJOUTE UNE FONCTION QUI SUPPRIME LE CRÉNEAU SI ON CLIQUE DESSUS */
        new_creneau.addEventListener('click', () => supprimerCreneau(new_creneau));
      });
    });
}


/** Envoyer une requête au serveur pour supprimer le créneau passé en paramètre
 * puis update l'affichage */
function supprimerCreneau(creneauElement) {
  // Appel à l'api pour ajouter une personne à la BDD
  fetch(`http://localhost:8080/Volundr/rest/tutorial/delCasesEvent/${id}`, {
    method: "post",
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    /* seul l'id est utile à envoyer pour la suppression
     * attention: c'est une liste qu'il faut envoyer même si un seul élément */
    body: JSON.stringify([{ "id": creneauElement.getAttribute('data-id') }])

  }).then((response) => {
    //do something awesome that makes the world a better place
    console.log(response)

    // Appel à l'api pour update l'affichage
    updateAffichage();
  });
}


/** Renvoie le JSON à envoyer pour la requête de création de créneaux */
function addCreneauxReqJSON() {
  return Array.from(scheduler.getElementsByTagName('volundr-room')).flatMap(room => {
    let roomJSON = { "id": room.getAttribute('data-id') };
    return Array.from(room.getElementsByTagName('volundr-creneau'), c => {
      let result = {
        "debutCreneau": c.time,
        "finCreneau": c.time + c.duration,
        "salleC": roomJSON
      };

      /* important de récupérer l'id sinon quand on va rappeler le serveur
       * on va créer une copie au lieu de modifier l'original */
      if (c.getAttribute('data-id')) {
        result["id"] = c.getAttribute('data-id');
      }
      return result;
    });
  });
}


addSalle.addEventListener('click', () =>
  addSalleScheduler(choixSalle.value, choixSalle.selectedOptions[0].text));


addCreneau.addEventListener('click', () => {
  /* n'executer la fonction que si on est pas déjà en train
   * de placer un créneau */
  if (scheduler.caseHoverCallback.length > 0) {
    return;
  }

  /* pour placer le créneau, on veut cliquer sur les horaires et non sur
   * le créneau lui-même */
  scheduler.classList.remove("click-creneau");

  let new_creneau = new Creneau();
  new_creneau.setAttribute('data-duration', choixDuree.value);

  scheduler.caseHoverCallback = [
    ((room, time) => {
      if ((time + new_creneau.duration) <= room.timeEnd) {
        new_creneau.remove();
        new_creneau.setAttribute('data-time', time);
        room.appendChild(new_creneau);
      }
    })
  ]

  /* Quand on clique, on va envoyer une requête pour mettre à jour les créneaux
   * sur le serveur puis recharger la liste des créneaux */
  scheduler.caseClickCallback = [
    ((room, time) => {
      document.getElementsByTagName('volundr-scheduler')[0].caseClickCallback = [];
      document.getElementsByTagName('volundr-scheduler')[0].caseHoverCallback = [];

      /* on clique à nouveau sur les créneaux plutôt que sur les horaires */
      scheduler.classList.add("click-creneau");

      console.log(addCreneauxReqJSON());

      // Appel à l'api pour ajouter une personne à la BDD
      fetch(`http://localhost:8080/Volundr/rest/tutorial/addCasesEvent/${id}`, {
        method: "post",
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(addCreneauxReqJSON())

      }).then((response) => {
        //do something awesome that makes the world a better place
        console.log(response)

        // Appel à l'api pour update l'affichage
        updateAffichage();
      });
    })
  ]

});

/* Quand on clique sur le lien, on lance l'algorithme d'affectation sur le
 * serveur et on refresh l'affichage */
runAfectation.addEventListener('click', () => {
  fetch(`http://localhost:8080/Volundr/rest/tutorial/runAffectationEvent/${id}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        updateAffichage();
        console.log(response);
      });
});

// actualiser l'affichage au chargement de la page
window.onload = updateAffichage;
