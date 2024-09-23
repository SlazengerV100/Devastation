import { useEffect, useState } from 'react';
import LoadingStage from '../stages/LoadingStage.jsx';
import { useAtomValue, useAtom } from 'jotai';
import { connectionStatusAtom, localCharacterAtom } from '../js/atoms.js';
import CharacterSelectStage from "../stages/CharacterSelectStage.jsx";
import GameStage from "../stages/GameStage.jsx";
import {connect} from "./connectionManager.js";

const StageManager = () => {
    const [connectionStatus, setConnectionStatus] = useAtom(connectionStatusAtom);
    const [storedPlayer, setStoredPlayer] = useAtom(localCharacterAtom);
    const [currentStage, setCurrentStage] = useState(<LoadingStage />);

    const attemptConnect = async () => {
        try {
            await connect();
            setConnectionStatus('connected');
        } catch (error) {
            console.error('Connection failed:', error);
            setConnectionStatus('disconnected');
        }
    };

    useEffect(() => {
        attemptConnect().then(r => console.log("Connection established!"))
    }, []);

    useEffect(() => {
        const checkStoredPlayer = () => {
            const player = localStorage.getItem('playerID');
            if(player){
                setStoredPlayer(prev => ({
                    ...prev,
                    playerName: player
                }));
            }
        };

        // Check initially when the component mounts
        checkStoredPlayer();
    }, [setStoredPlayer]);

    // Trigger stage update based on connection status and storedPlayer changes
    useEffect(() => {
        console.log('CHANGED')
        console.log(storedPlayer)
        if (connectionStatus === 'disconnected') {
            setCurrentStage(<LoadingStage />);
        } else if (storedPlayer.playerName) {
            setCurrentStage(<GameStage />);
        } else {
            setCurrentStage(<CharacterSelectStage />);
        }
    }, [connectionStatus, storedPlayer.playerName]); // Effect depends on connectionStatus and storedPlayer


    return (
        <>
            {currentStage}
        </>
    );
};

export default StageManager;
