import Scheduler from "./Scheduler.js";
import Room from "./Room.js";
import Creneau from "./Creneau.js";

customElements.define('volundr-scheduler', Scheduler);
customElements.define('volundr-room', Room);
customElements.define('volundr-creneau', Creneau);

const id = new URLSearchParams(window.location.search).get('id');
/* le mail de l'utilisateur qui accède à la page */
const user = new URLSearchParams(window.location.search).get('user');

const scheduler = document.getElementById('scheduler');

const choixOui = document.getElementById('choix-oui');
const choixNon = document.getElementById('choix-non');
const choixMaybe = document.getElementById('choix-maybe');

/** Récupérer les choix déjà fait par l'utilisateur depuis le serveur
 * et mettre à jour l'affichage */
function getUserChoices(params) {
  // Récupérer tous les choix fait par l'utilisateur pour cet événement
  fetch(`/rest/tutorial/getChoixEvent/${id}/${user}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      data.map(choix => {
        document.getElementById(`volundr-creneau-${choix.caseCh.id}`);
      });
    });
}

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
  // Récupérer la liste des créneaux de l'event et les ajouter dans le scheduler
  fetch(`/rest/tutorial/getChoixAffect/${id}/${user}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(creneau => {
      /* on supprime tous les anciens créneaux */
      Array.from(scheduler.getElementsByTagName('volundr-creneau')).forEach(e => e.remove());

      /* on s'assure que la salle existe */
      addSalleScheduler(creneau.salleC.id,
        `${creneau.salleC.batiment}.${creneau.salleC.numero}`);

      /* on crée l'objet 'volundr-creneau' et on l'ajoute au scheduler */
      let new_creneau = new Creneau();
      new_creneau.setAttribute('data-time', creneau.debutCreneau);
      new_creneau.setAttribute('data-duration',
        creneau.finCreneau - creneau.debutCreneau);

      /* important de récupérer l'id pour pouvoir sélectionner le créneau */
      new_creneau.setAttribute('data-id', creneau.id);

      /* on ajoute un id à l'objet html du créneau basé sur son data-id pour
       * facilement pouvoir y accéder,
       * même si ça peut sembler doublon avec data-id */
      new_creneau.setAttribute('id', `volundr-creneau-${creneau.id}`);

      new_creneau.innerText = `${creneau.eventName}`;
      new_creneau.style.backgroundColor = 'yellow';

      document.getElementById(`volundr-room-${creneau.salleC.id}`).appendChild(new_creneau);

    });
}

// actualiser l'affichage au chargement de la page
window.onload = updateAffichage;