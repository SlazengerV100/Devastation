import { useAtom } from "jotai";
import { localCharacterAtom } from "../js/atoms.js";
import { sendPlayerMovement } from "../managers/connectionManager";

const useUpdatePosition = () => {
    const [localCharacter, setLocalCharacter] = useAtom(localCharacterAtom);

    const updatePosition = (direction) => {

        let x = localCharacter.x;
        let y = localCharacter.y;
        let name = localCharacter.playerName;

        switch (direction) {
            case "UP":
                y--;
                break;
            case "DOWN":
                y++;
                break;
            case "LEFT":
                x--;
                break;
            case "RIGHT":
                x++;
                break;
            default:
                console.error("Invalid direction");
        }

        setLocalCharacter({
            playerName: name,
            x,
            y
        });

        sendPlayerMovement(name, direction);
    };

    return updatePosition;
};

export default useUpdatePosition;
