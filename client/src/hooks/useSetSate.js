import { useAtom } from "jotai";
import { localCharacterAtom } from "../js/atoms.js";
import { sendPlayerMovement } from "../managers/connectionManager";

const useSetSate = () => {
    const [localCharacter, setLocalCharacter] = useAtom(localCharacterAtom);

    const setState = (state) => {
        setLocalCharacter((prev) => ({
            ...prev,
            characterX: state.playerMap.Developer.position.x,
            characterY: state.playerMap.Developer.position.y
        }));
    };

    return setState
}

export default useSetSate