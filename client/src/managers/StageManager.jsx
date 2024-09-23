import { useEffect, useState } from 'react';
import LoadingStage from '../stages/LoadingStage.jsx';
import CharacterSelectStage from "../stages/CharacterSelectStage.jsx";
import GameStage from "../stages/GameStage.jsx";
import { useAtom } from 'jotai';
import { connectionStatusAtom, localCharacterAtom } from '../js/atoms.js';
import { connect } from "./connectionManager.js";
import {init} from "../js/spriteFrameGrabber.js";

const StageManager = () => {
    const [connectionStatus, setConnectionStatus] = useAtom(connectionStatusAtom);
    const [storedPlayer, setStoredPlayer] = useAtom(localCharacterAtom);

    const [currentStage, setCurrentStage] = useState(<GameStage />);
    const [loading, setLoading] = useState(true)
    // Initial useEffect for asset initialization and connection attempt
    useEffect(() => {
        const initialize = async () => {
            setLoading(true)
            await init();
            setLoading(false)
            await attemptConnect();
            console.log("Connection established!");
        };

        initialize();
    }, []);

    // Check for stored player in localStorage
    useEffect(() => {
        const checkStoredPlayer = () => {
            const player = localStorage.getItem('playerID');
            if (player) {
                setStoredPlayer(prev => ({
                    ...prev,
                    playerName: player
                }));
            }
        };
        checkStoredPlayer();
    }, [setStoredPlayer]);

    useEffect(() => {
        // if (connectionStatus === 'disconnected') {
        //     setCurrentStage(<LoadingStage />);
        // } else if (storedPlayer.playerName) {
        setCurrentStage(<GameStage />);
        //} else {
        // setCurrentStage(<CharacterSelectStage />);
        //}
    }, [connectionStatus, storedPlayer.playerName]);

    const attemptConnect = async () => {
        try {
            await connect();
            setConnectionStatus('connected');
        } catch (error) {
            console.error('Connection failed:', error);
            setConnectionStatus('disconnected');
        }
    };

    return (
       <>
           {
               loading ?
               <h1>Loading assets TODO fix this, its ugly</h1>
               :
               currentStage
           }
       </>
    );
};

export default StageManager;
