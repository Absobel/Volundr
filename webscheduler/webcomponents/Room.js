/** convertir un string en nb de minutes en tenant compte de l'unité */
function toTime(time) {
  if (time.split('h').length > 1) {
    return Number(time.split('h')[0]) * 60;
  } else {
    return Number(time);
  }
}


/** pgcd de deux entiers */
function gcd(num1, num2) {
  if (num2 == 0)
  return num1;
  return gcd(num2, num1 % num2);
}


/** plus petit commun multiple d'un tableau */
function lcm_of_array(arr) {
  let lcm = arr[0];
  for (let i = 1; i < arr.length; i++) {
    let num1 = lcm;
    let num2 = arr[i];
    let gcd_val = gcd(num1, num2);
    lcm = (lcm * arr[i]) / gcd_val;
  }
  return lcm;
}


export default class Room extends HTMLElement {

  constructor () {
    super();

    this.cases = document.createElement('ul');
    this.cases.classList.add('cases');

    this.creneaux = document.createElement('ul');
    this.creneaux.classList.add('creneaux');

    this.nbCreneaux = 0;

    this.updateCreneauxList();

    this.appendChild(this.cases);
    this.appendChild(this.creneaux);
  }

  /** Nettoyer la liste des créneaux quand un nouveau fils
   * est ajouté ou supprimé */
  updateCreneauxList () {

    /* supprimer les li vides ou elements qui ont rien à faire là */
    Array.from(this.creneaux.children).forEach(element => {

      switch (element.tagName) {
        case "LI":
          if (element.childElementCount === 0) {
            element.remove();
          }
          break;
        case "VOLUNDR-CRENEAU":
          console.log("test");
          let creneau = document.createElement('li');
          creneau.appendChild(element);
          this.creneaux.appendChild(creneau);
          break;
        default:
          element.remove();
          break;
      }
    });

    /* ajouter les nouveaux créneaux à la liste des créneaux */
    Array.from(this.children).forEach(element => {
      if (element.tagName === "VOLUNDR-CRENEAU") {
        let creneau = document.createElement('li');
        creneau.appendChild(element);
        this.creneaux.appendChild(creneau);
      }
    });

    /* mettre à jour tout le reste */
    this.updateGridColumns();
    Room.observedAttributes.map(x => this.setAttribute(x, this.getAttribute(x)))
  }

  static get observedAttributes() { return ['data-name', 'data-time-start', 'data-time-end']; }

  attributeChangedCallback (name, oldValue, newValue) {
    if (newValue === null) {
      return
    }

    switch (name) {
      case 'data-time-start', 'data-time-end':
        let nbCases = toTime(this.getAttribute('data-time-end'))
          - toTime(this.getAttribute('data-time-start'));

        this.creneaux.style.gridTemplateRows = `repeat(${nbCases}, 1fr)`;

        let nbLi = Math.ceil(nbCases / 15);

        while (this.cases.childElementCount > nbLi) {
          this.cases.removeChild(this.cases.lastChild);
        }
        while (this.cases.childElementCount < nbLi) {
          this.cases.appendChild(document.createElement('li'));
        }

        break;
    }

  }

  /** mettre le bon nombre de colonnes à la grille css des créneaux
   * et mettre à jour les créneaux pour qu'ils aient la bonne largeur */
  updateGridColumns () {
    let timeStart = toTime(this.getAttribute('data-time-start'));
    let liLen = Array(this.cases.childElementCount).fill(0);
    let creneaux = Array.from(this.getElementsByTagName('volundr-creneau'));

    /* calculer les entrelacement entre les créneaux */
    creneaux.forEach(el => {
      for (let i = el.time; i < (el.time + el.duration); i += 15) {
        liLen[(i - timeStart) / 15]++;
      }
    });

    /* Attention à bien retirer les valeurs nulles
     * avant de calculer le lcm */
    let nbColonnes = lcm_of_array(liLen.filter(x => x != 0));

    /* changer le nombre de colonnes de la grille css */
    this.creneaux.style.gridTemplateColumns = `repeat(${nbColonnes}, 1fr)`;

    /* changer la largeur des créneaux dans l'affichage */
    creneaux.forEach(el => {
      let maxNbEntrelacement = 1;
      for (let i = el.time; i < (el.time + el.duration); i += 15) {
        let pos = (i - timeStart) / 15;
        if (liLen[pos] > maxNbEntrelacement) {
          maxNbEntrelacement = liLen[pos];
        }
      }

      el.parentElement.style.gridColumn = `span ${nbColonnes / maxNbEntrelacement}`;
    });
  }
}
