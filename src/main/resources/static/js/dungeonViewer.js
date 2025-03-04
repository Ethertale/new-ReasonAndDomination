document.addEventListener("DOMContentLoaded", function () {
    const pathParts = window.location.pathname.split('/');
    const dungeonTitle = pathParts[pathParts.length - 1];
    let playersText = '';

    if (!dungeonTitle) {
        console.error("Dungeon title is missing in the URL.");
        return;
    }

    fetch(`/api/dungeons/${dungeonTitle}`)
        .then(response => response.json())
        .then(dungeon => {
            if (!dungeon) {
                console.error("Dungeon not found.");
                return;
            }

            document.getElementById('dungeon-name').textContent = dungeon.name;
            document.getElementById('dungeon-image').src = dungeon.image;
            document.getElementById('dungeon-description').textContent = dungeon.description;
            document.getElementById('dungeon-map').src = '/' + dungeon.imageMap;
            document.getElementById('dungeon-level').textContent = 'Level: ' + dungeon.level;
            document.getElementById('dungeon-territory').textContent = 'Territory: ' + (dungeon.territory || 'PLACEHOLDER');
            document.getElementById('dungeon-instance-type').textContent = 'Instance Type: ' + dungeon.dungeonType;

            if (dungeon.dungeonSize === 'DUNGEON_10') {
                playersText = 'Number of Players: 10';
            } else if (dungeon.dungeonSize === 'RAID_20') {
                playersText = 'Number of Players: 20';
            } else if (dungeon.dungeonSize === 'RAID_40') {
                playersText = 'Number of Players: 40';
            }
            document.getElementById('dungeon-players').textContent = playersText;
            document.getElementById('dungeon-zone-id').textContent = 'Zone ID: ' + dungeon.id;
            document.getElementById('dungeon-final-boss').textContent = 'Final Boss: ' + (dungeon.finalBoss || 'PLACEHOLDER');
        })
        .catch(error => {
            console.error('Error fetching dungeon data:', error);
        });
});