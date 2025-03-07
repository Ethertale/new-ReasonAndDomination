const slides = document.querySelectorAll('.slides img');
let slideIndex = 0;
let intervalId = null;
const intervalTimer = 30000;
const raceName = document.querySelector('#race-name');
const raceDescription = document.querySelector('#race-description');
const raceFaction = document.querySelector('#race-faction');
const raceFactionName = document.querySelector('#race-faction-name');

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
            raceName.textContent = "Automaton";
            raceDescription.textContent = "Once bound to servitude in the great factories of Grafset, the Automatons—sentient constructs of brass and steel—have long since broken free from their creators' control. Now, these self-sufficient beings seek purpose beyond their original programming, forging their own destiny in a world that still sees them as mere machines. Though some embrace their past as loyal defenders of civilization, others resent their makers and carve out a future built on autonomy and strength. Whatever path they choose, the Automatons stand as unwavering symbols of invention, resilience, and the boundless potential of technology.";
            raceFaction.src = "/imgs/blessed-faction-logo.png"
            raceFactionName.textContent = "BLESSED";
            break;
        case 1:
            raceName.textContent = "Avian";
            raceDescription.textContent = "Hailing from the towering spires of Conterel, the Avian people are masters of the skies, their sharp talons and keen eyes unmatched in aerial combat. Natural-born navigators, they command the winds with an almost supernatural instinct, making them invaluable pilots and scouts for those who dare to sail the tempestuous skies. Though many Avians find a home among airship fleets and skyward citadels, their true allegiance remains to their own kind, ever watchful of those who seek to ground them. To threaten an Avian’s freedom is to invite the wrath of a tempest unleashed.";
            raceFaction.src = "/imgs/unsung-faction-logo.png"
            raceFactionName.textContent = "UNSUNG";
            break;
        case 2:
            raceName.textContent = "Dwarf";
            raceDescription.textContent = "Deep within the smoking depths of Rimrior’s forges, the Dwarves of Bul’Dor hammer steel and shape the world with their mastery of metal and steam. These steadfast engineers and metallurgists form the backbone of the industrial revolution, their underground cities humming with the ceaseless clatter of great machines. Proud and unyielding, Dwarves live by the sweat of their brow and the strength of their forge-wrought creations. They fight with unwavering determination, their war machines and steam-powered armor making them a force to be reckoned with. To stand against the Dwarves is to stand against progress itself.";
            raceFaction.src = "/imgs/blessed-faction-logo.png"
            raceFactionName.textContent = "BLESSED";
            break;
        case 3:
            raceName.textContent = "Elf";
            raceDescription.textContent = "The Elves of Yarsay have abandoned the whispering woods of their ancestors, choosing instead to shape the world with the power of invention. In the grand metropolis of Ayotalyra, arcane energies intertwine with machinery, forging a society where elegance and industry walk hand in hand. These Elves are both artisans and visionaries, seeking to perfect the union of magic and technology. Though their advancements are awe-inspiring, not all view their work with favor—many among their kin believe they have forsaken their heritage. Yet, the Elves of Yarsay move ever forward, determined to shape a future where the old ways bow before progress.";
            raceFaction.src = "/imgs/elf-faction-logo.png"
            raceFactionName.textContent = "NEUTRAL";
            break;
        case 4:
            raceName.textContent = "Ethereal";
            raceDescription.textContent = "Drifting between the physical and the unknown, the Ethereals of Ashonethia are beings of pure energy, wrapped in veils of shimmering cloth and shifting light. Born from the strange and unpredictable forces of ether-based energy, they are scholars, mystics, and wanderers, forever seeking to understand the unseen currents that weave reality together. To many, they appear ghostly and insubstantial, their motives as enigmatic as the storms of Exmere from which they hail. While some fear their arcane knowledge, others recognize their wisdom as a guiding light in a world consumed by steam and steel.";
            raceFaction.src = "/imgs/unsung-faction-logo.png"
            raceFactionName.textContent = "UNSUNG";
            break;
        case 5:
            raceName.textContent = "Gnome";
            raceDescription.textContent = "In the bustling streets of Sloburg, the Gnomes of Grafset are the architects of chaos and creation, their boundless curiosity giving birth to the most ingenious—and dangerous—contraptions the world has ever seen. These small but formidable inventors thrive on experimentation, their restless minds forever conjuring new machines, gadgets, and, on occasion, disasters. Though their explosive tendencies make them unpredictable allies, none can deny the impact of Gnomish ingenuity. Whether crafting intricate automatons or weaponizing the impossible, Gnomes stand as pioneers of the industrial age, proving that true genius often walks hand in hand with madness.";
            raceFaction.src = "/imgs/blessed-faction-logo.png"
            raceFactionName.textContent = "BLESSED";
            break;
        case 6:
            raceName.textContent = "Human";
            raceDescription.textContent = "The industrious people of Sharenth have built their legacy upon innovation and ambition, shaping the great cities of Portsden into the beating heart of the modern age. With steam and steel, they push the boundaries of possibility, constructing vast factories, sprawling metropolises, and powerful war machines. Though they lack the mystical heritage of other races, their strength lies in adaptability and determination, allowing them to stand at the forefront of technological advancement. Whether as noble leaders or ruthless industrialists, the Humans of Portsden will stop at nothing to carve their mark upon history.";
            raceFaction.src = "/imgs/blessed-faction-logo.png"
            raceFactionName.textContent = "BLESSED";
            break;
        case 7:
            raceName.textContent = "Orc";
            raceDescription.textContent = "Scarred by war and reforged in iron, the Orcs of Savadon are a testament to survival through sheer might. Once proud warriors, they have adapted to the age of steam by augmenting their bodies with brutal mechanical enhancements—steam-powered limbs, armored plating, and weapons fused to flesh. These war-hardened titans carve out their existence in the smog-choked city of Tizrezar, where only the strongest endure. Bound by no law but their own, they march forward, driven by an unrelenting will to dominate a world that once sought to break them.";
            raceFaction.src = "/imgs/unsung-faction-logo.png"
            raceFactionName.textContent = "UNSUNG";
            break;
        case 8:
            raceName.textContent = "Smogborn";
            raceDescription.textContent = "Born from the poisoned breath of Savadon’s industry, the Smogborn are a twisted evolution of life itself—creatures molded by the toxic smog and alchemical runoff that choke the streets of Tizrezar. With skin hardened against pollution and lungs adapted to breathe the filth others cannot, they have turned the wastelands into their dominion. Considered outcasts and abominations by some, they embrace their mutation as a gift, thriving where others wither. Hardened by a world that abandoned them, the Smogborn fight not for glory or conquest, but for the right to exist in the ashes of progress.";
            raceFaction.src = "/imgs/unsung-faction-logo.png"
            raceFactionName.textContent = "UNSUNG";
            break;
    }
}