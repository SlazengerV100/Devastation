import { store } from '../App';
import { playerMap, localPlayerId } from "./atoms.js";

export default function keyHandler(sendPlayerMovement) {

    // Helper function to handle key actions
    const handleKeyPress = (key) => {
        // Get the player map state
        const players = store.get(playerMap);
        const localPlayerIdValue = store.get(localPlayerId);
        const localCharacter = players[localPlayerIdValue]; // Get the local player's data

        if (!localCharacter) {
            console.warn("Local character not found in playerMap.");
            return;
        }

        let newPosition = { ...localCharacter };

        switch (key) {
            case 'w':
                // Move player up
                console.log("W pressed");
                sendPlayerMovement('UP');
                break;
            case 'a':
                // Move player left
                console.log("A pressed");
                sendPlayerMovement('LEFT');
                break;
            case 's':
                // Move player down
                console.log("S pressed");
                sendPlayerMovement('DOWN');
                break;
            case 'd':
                // Move player right
                console.log("D pressed");
                sendPlayerMovement('RIGHT');
                break;
            case 'o':
                // Pick up ticket
                console.log("O pressed");
                // sendPlayerAction('PICKUP'); // Replace with your actual backend action
                break;
            case 'p':
                // Pick drop
                console.log("P pressed");
                // sendPlayerAction('DROP'); // Replace with your actual backend action
                break;
            default:
                return; // Exit if the key is not handled
        }

        // Update the playerMap with the new position
        store.set(playerMap, (prev) => ({
            ...prev,
            [localPlayerIdValue]: newPosition,
        }));
    };

    // Keydown event listener
    const onKeyDown = (event) => {
        const key = event.key.toLowerCase();
        console.log("keyDown:" + event.type);
        if (['w', 'a', 's', 'd', 'o', 'p'].includes(key)) {
            handleKeyPress(key);
        }
    };

    // Attach event listeners when the component is mounted
    document.addEventListener('keydown', onKeyDown);

    // Cleanup event listeners when the component is unmounted
    return () => {
        document.removeEventListener('keydown', onKeyDown);
    };
}
