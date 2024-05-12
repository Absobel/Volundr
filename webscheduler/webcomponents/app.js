import Scheduler from "./Scheduler.js";
import Room from "./Room.js";
import Creneau from "./Creneau.js";

customElements.define('volundr-scheduler', Scheduler);
customElements.define('volundr-room', Room);
customElements.define('volundr-creneau', Creneau);

document.getElementById('add').addEventListener('click', () => {
  let test = new Creneau();
  test.setAttribute('data-duration', 45);
  test.textContent = 'téo pisenti';

  test.addEventListener("click", () => test.remove());

  document.getElementsByTagName('volundr-scheduler')[0].caseHoverCallback = [
    ((room, time) => {
      if ((time + test.duration) <= room.timeEnd) {
        test.remove();
        test.setAttribute('data-time', time);
        room.appendChild(test);
      }
    })
  ]
});

document.getElementsByTagName('volundr-scheduler')[0].caseClickCallback = [
  ((room, time) => {
    document.getElementsByTagName('volundr-scheduler')[0].caseHoverCallback = [];
    console.log(room);
    console.log(time);
  })
]

Array.from(document.getElementsByTagName('volundr-creneau')).map(x => x.addEventListener("click", () => x.remove()))
