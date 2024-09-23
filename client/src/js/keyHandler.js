// This function sets up key listeners for handling player movements (W, A, S, D) and actions (O, P).
import { store } from '../App'
import {localCharacterAtom} from "./atoms.js";

export default function keyHandler(sendPlayerMovement) {
    // Helper function to handle key actions
    const handleKeyPress = (key) => {
        switch (key) {
            case 'w':
                // Move player up
                console.log("W pressed")
                store.set(localCharacterAtom, (prev) => ({
                    ...prev,
                    y: prev.y - 1,
                }));
                //sendPlayerMovement('up'); // Send movement to backend
                break;
            case 'a':
                // Move player left
                console.log("A pressed")
                store.set(localCharacterAtom, (prev) => ({
                    ...prev,
                    x: prev.x - 1,
                }));
                //sendPlayerMovement('left');
                break;
            case 's':
                // Move player down
                console.log("S pressed")
                store.set(localCharacterAtom, (prev) => ({
                    ...prev,
                    y: prev.y + 1,
                }));
                //sendPlayerMovement('down');
                break;
            case 'd':
                // Move player right
                console.log("D pressed")
                store.set(localCharacterAtom, (prev) => ({
                    ...prev,
                    x: prev.x + 1,
                }));
                //sendPlayerMovement('right');
                break;
            case 'o':
                // Pick up ticket
                console.log("O pressed")
                //sendPlayerMovement('ticketPickUp'); // Replace with your actual backend action
                break;
            case 'p':
                // Pick drop
                console.log("P pressed")
                //sendPlayerMovement('ticketDrop'); // Replace with your actual backend action
                break;
            default:
                break;
        }
    };

    // Keydown event listener
    const onKeyDown = (event) => {
        const key = event.key.toLowerCase();
        console.log("keyDown:" + event.type)
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
