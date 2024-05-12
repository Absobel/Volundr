export default class Scheduler extends HTMLElement {

  constructor () {
    super();

    this.salles = document.createElement('ul');
    this.salles.classList.add('salles');

    this.lastHover = null;

    /* Liste de fonctions de callback pour quand on passe la souris sur
     * une horaire */
    this.caseHoverCallback = [];

    /* Liste de fonctions de callback pour quand on clique sur
     * une horaire */
    this.caseClickCallback = [];
  }

  connectedCallback() {
    let spacer = document.createElement('li');
    spacer.classList.add('spacer');
    this.salles.appendChild(spacer);

    this.appendChild(this.salles);

    /* On conseille de forcer le display à none dans
     * le html pour éviter que ça clignote. On rend à nouveau visible ici */
    this.style = "";

    this.lock = false;
  }

  /** Nettoyer et actualiser la liste des salles quand un nouveau fils
   * est ajouté ou supprimé */
  updateSallesList () {
    if (this.lock) { return }
    this.lock = true;

    /* supprimer les li vides ou elements qui ont rien à faire là */
    let isPreviousSpacer = false; /* on supprime les spacer
                                     que s'il y en a 2 d'affilée */
    let aSupprimer = []; /* ne pas supprimer dans la boucle car sinon
                            ça donne des résultats indéterminés */
    for (let element of this.salles.children) {
      switch (element.tagName) {
        case "LI":
          if (element.classList.contains('spacer')) {
            if (isPreviousSpacer) {
              aSupprimer.push(element);
            }
            isPreviousSpacer = true;
          } else {
            if (element.childElementCount === 0) {
              aSupprimer.push(element);
            } else {
              isPreviousSpacer = false;
            }
          }
          break;
        case "VOLUNDR-ROOM":
          this.appendChild(element);
          break;
        default:
          aSupprimer.push(element);
          break;
      }
    }

    aSupprimer.map(x => x.remove());

    /* ajouter les nouvelles salles à la liste des salles */
    let aAppend = [];
    for (let element of this.children) {
      if (element.tagName === "VOLUNDR-ROOM") {
        let salle = document.createElement('li');
        salle.appendChild(element);
        aAppend.push(salle);

        let spacer = document.createElement('li');
        spacer.classList.add('spacer');
        aAppend.push(spacer);
      }
    }
    aAppend.map(x => this.salles.appendChild(x));

    this.lock = false;
  }
}
