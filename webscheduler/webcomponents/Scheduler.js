export default class Scheduler extends HTMLElement {

  constructor () {
    super();

    this.salles = document.createElement('ul');
    this.salles.classList.add('salles');

    Array.from(this.children).forEach((element, idx, array) => {
      var salle = document.createElement('li');
      salle.appendChild(element);
      this.salles.appendChild(salle);

      if (idx !== array.length - 1) {
        var spacer = document.createElement('li');
        spacer.classList.add('spacer');
        this.salles.appendChild(spacer);
      }
    });

    this.appendChild(this.salles);

    this.lastHover = null;

    /* Liste de fonctions de callback pour quand on passe la souris sur
     * une horaire */
    this.caseHoverCallback = [];
  }

  connectedCallback() {
    /* On conseille de forcer le display à none dans
     * le html pour éviter que ça clignote. On rend à nouveau visible ici */
    this.style = "";
  }

}
