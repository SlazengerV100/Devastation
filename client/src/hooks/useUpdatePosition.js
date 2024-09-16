import { useAtom } from "jotai";
import { localCharacterAtom } from "../js/atoms.js";
import { sendPlayerMovement } from "../managers/connectionManager";

const useUpdatePosition = () => {
    const [localCharacter, setLocalCharacter] = useAtom(localCharacterAtom);

    const updatePosition = (direction) => {
        let x = localCharacter.characterX;
        let y = localCharacter.characterY;
        let name = localCharacter.characterName;

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
            characterName: name,
            characterX: x,
            characterY: y
        });

        sendPlayerMovement(name, direction);
    };

    return updatePosition;
};

export default useUpdatePosition;
