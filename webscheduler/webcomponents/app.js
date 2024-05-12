import Scheduler from "./Scheduler.js";
import Room from "./Room.js";
import Creneau from "./Creneau.js";

customElements.define('volundr-scheduler', Scheduler);
customElements.define('volundr-room', Room);
customElements.define('volundr-creneau', Creneau);

let test = new Creneau();
test.setAttribute('data-duration', 45);
test.textContent = 'téo pisenti';

document.getElementsByTagName('volundr-scheduler')[0].caseHoverCallback = [
  ((room, time) => {
    if ((time + test.duration) <= room.timeEnd) {
      test.remove();
      test.setAttribute('data-time', time);
      room.appendChild(test);
    }
  })
]
