function toTime(time) {
  if (time.split('h').length > 1) {
    return Number(time.split('h')[0]) * 60;
  } else {
    return Number(time);
  }
}


export default class Creneau extends HTMLElement {

  constructor () {
    super();
    this.time = 0;
    this.duration = 0;
    this.room = null;
  }

  static get observedAttributes() { return ['data-time', 'data-duration']; }

  attributeChangedCallback (name, oldValue, newValue) {
    if (newValue === null || this.room === null) {
      return
    }

    switch (name) {
      case 'data-time':
        var timeStart = toTime(this.room.getAttribute('data-time-start'));
        this.time = toTime(newValue);

        /* attention grid-row-start doit commencer à 1 */
        this.parentElement.style.gridRowStart = `${this.time - timeStart + 1}`;
        break;

      case 'data-duration':
        this.duration = toTime(newValue);
        this.parentElement.style.gridRowEnd = `span ${this.duration}`;
        break;
    }

    this.room.updateGridColumns();
  }

  connectedCallback() {
    let oldRoom = this.room;
    this.room = this.closest('volundr-room');
    Creneau.observedAttributes.map(x => this.setAttribute(x, this.getAttribute(x)))

    if (this.room !== null) {
      this.room.updateCreneauxList();
    }
  }

  disconnectedCallback () {
    if (this.room !== null) {
      this.room.updateCreneauxList();
    }
  }
}
