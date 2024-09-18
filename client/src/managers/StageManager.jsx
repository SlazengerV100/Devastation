import { useMemo } from 'react';
import LoadingStage from '../stages/LoadingStage.jsx';
import { useAtomValue } from 'jotai';
import { connectionStatusAtom, localCharacterAtom } from '../js/atoms.js';
import CharacterSelectStage from "../stages/CharacterSelectStage.jsx";
import GameStage from "../stages/GameStage.jsx";

const StageManager = () => {
    const connectionStatus = useAtomValue(connectionStatusAtom);
    const localCharacter = useAtomValue(localCharacterAtom)

    // using useMemo to avoid unnecessary re-renders
    const currentStage = useMemo(() => {
        if (connectionStatus === 'disconnected') return <LoadingStage />;
        //if(localCharacter.characterName === '')
            return <CharacterSelectStage/>

        //return <GameStage/>

    }, [connectionStatus, localCharacter.characterName]);

    return (
        <>
            {currentStage}
        </>
    );
};

export default StageManager;
