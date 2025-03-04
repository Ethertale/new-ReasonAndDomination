document.addEventListener("DOMContentLoaded", function() {
    fetch("/api/dungeons")
        .then(response => response.json())
        .then(dungeons => {
            const dungeonList = document.getElementById("dungeon-list");
            dungeonList.innerHTML = "";

            dungeons.forEach(dungeon => {
                let dungeonElement = `
                        <div class="col-6 col-md-4 col-lg-2 d-flex align-items-center flex-column">
                            <img src="${dungeon.image}" alt="">
                            <a href="/dungeons/${dungeon.slug}">
                                <h3 class="text-center">${dungeon.name}</h3>
                            </a>
                            <p class="hideDrop">${dungeon.dungeonType}</p>
                            <p class="hideDrop">Level ${dungeon.level}</p>
                            ${dungeon.dungeonSize === 'DUNGEON_10' ? '<p class="hideDrop">10-Man</p>' : ''}
                            ${dungeon.dungeonSize === 'RAID_20' ? '<p class="hideDrop">20-Man</p>' : ''}
                            ${dungeon.dungeonSize === 'RAID_40' ? '<p class="hideDrop">40-Man</p>' : ''}
                        </div>
                    `;
                dungeonList.innerHTML += dungeonElement;
            });
        })
        .catch(error => console.error("Error loading dungeons:", error));
});