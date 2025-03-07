const slides = document.querySelectorAll('.slides img');
let slideIndex = 0;
let intervalId = null;
const intervalTimer = 30000;
const className = document.querySelector('#class-name');
const classDescription = document.querySelector('#class-description');
const classEmblem = document.querySelector('#class-emblem');
const availableTo = document.querySelector('#available-to');

initSlider();

function initSlider() {
    if (slides.length > 0) {
        slides[slideIndex].classList.add('displaySlide');
        intervalId = setInterval(nextSlide, intervalTimer);
    }
    textReplacer();
}

function showSlide(index) {

    if (index >= slides.length) {
        slideIndex = 0;
    } else if (index < 0) {
        slideIndex = slides.length - 1;
    }

    slides.forEach((slide) => {
        slide.classList.remove('displaySlide');
    })
    slides[slideIndex].classList.add('displaySlide');
}

function previousSlide() {
    clearInterval(intervalId);
    slideIndex--;
    showSlide(slideIndex);
    textReplacer();
}

function nextSlide() {
    clearInterval(intervalId);
    slideIndex++;
    showSlide(slideIndex);
    textReplacer();
}

function textReplacer() {
    switch (slideIndex) {
        case 0:
            className.textContent = "Cogcaster";
            classDescription.textContent = "Masters of arcane innovation, Cogcasters weave fire and ether into destructive forces, unleashing devastation upon their foes. Their mastery over frost magic also grants them the power to mend wounds, making them both formidable combatants and invaluable allies on the battlefield.";
            classEmblem.src = "/imgs/classes/cogcaster-emblem.png"
            availableTo.textContent = "HUMAN, AUTOMATON, GNOME, ELF, ETHERIAL";
            break;
        case 1:
            className.textContent = "Enforcer";
            classDescription.textContent = "Brutes of unmatched strength, Enforcers dominate the battlefield with raw power and relentless fury. Whether wielding a colossal two-handed weapon, dual blades for unyielding aggression, or a shield for steadfast defense, they are the unbreakable front line of any warband.";
            classEmblem.src = "/imgs/classes/enforcer-emblem.png"
            availableTo.textContent = "HUMAN, AUTOMATONS, AVIANS, DWARF, SMOGBORN";
            break;
        case 2:
            className.textContent = "Occultist";
            classDescription.textContent = "Tapping into the forbidden arts, Occultists wield dark magic to wither their enemies with unholy curses. They strike down foes with bursts of malevolent power and twist the same darkness to mend their wounds, thriving in the delicate balance between life and decay.";
            classEmblem.src = "/imgs/classes/occultist-emblem.png"
            availableTo.textContent = "DWARF, GNOME, ORC, ETHERIAL";
            break;
        case 3:
            className.textContent = "Sentinel";
            classDescription.textContent = "Warriors of unwavering conviction, Sentinels channel divine energy to strike down their enemies with righteous fury. Whether wielding a mighty greatsword, defending with a shield, or healing the wounded with sacred light, they are the steadfast protectors of their allies.";
                classEmblem.src = "/imgs/classes/sentinel-emblem.png"
            availableTo.textContent = "HUMAN, DWARF, ELF";
            break;
        case 4:
            className.textContent = "Shaper";
            classDescription.textContent = "Attuned to the primal forces of nature, Shapers command the elements in battle. Fire and lightning bring destruction from afar, earth and air empower them in close combat, while water grants them the ability to heal and sustain their allies.";
            classEmblem.src = "/imgs/classes/shaper-emblem.png"
            availableTo.textContent = "AVIANS, ELF, ORC, SMOGBORN, ETHERIAL";
            break;
        case 5:
            className.textContent = "Tinkerer";
            classDescription.textContent = "Ingenious and deadly, Tinkerers thrive in chaos. Armed with twin blades coated in lethal poisons, they strike from the shadows or overwhelm with blinding speed. Some hone their agility to such heights that they can even deflect blows, making them elusive yet formidable tanks.";
            classEmblem.src = "/imgs/classes/tinkerer-emblem.png"
            availableTo.textContent = "AUTOMATONS, AVIANS, GNOME, ETHERIAL";
            break;
        case 6:
            className.textContent = "Tracker";
            classDescription.textContent = "Masters of the hunt, Trackers strike with precision from a distance, wielding bows and firearms with deadly efficiency. Up close, they employ traps and polearms to ensnare their prey. With a loyal beast by their side, they are never truly alone in the wilds.";
            classEmblem.src = "/imgs/classes/tracker-emblem.png"
            availableTo.textContent = "HUMAN, AVIANS, DWARF, SMOGBORN";
            break;
    }
}