import { useEffect, useState } from 'react';
import LoadingStage from '../stages/LoadingStage.jsx';
import CharacterSelectStage from "../stages/CharacterSelectStage.jsx";
import GameStage from "../stages/GameStage.jsx";
import { useAtom } from 'jotai';
import { connectionStatusAtom, playerMap, localPlayerId } from '../js/atoms.js';
import { connect } from "./connectionManager.js";
import {init} from "../js/spriteFrameGrabber.js";

const StageManager = () => {
    const [connectionStatus, setConnectionStatus] = useAtom(connectionStatusAtom);
    const [localPlayerIdValue, setLocalPlayerIdValue] = useAtom(localPlayerId);

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
    // useEffect(() => {
    //     const checkStoredPlayer = () => {
    //         const player = localStorage.getItem('playerID');
    //         if (player) {
    //             setStoredPlayer(prev => ({
    //                 ...prev,
    //                 playerName: player
    //             }));
    //         }
    //     };
    //     checkStoredPlayer();
    // }, [setStoredPlayer]);

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

    // useEffect(() => {
    //     const checkStoredPlayer = () => {
    //         const player = localStorage.getItem('playerID');
    //         if(player){
    //             setStoredPlayer(prev => ({
    //                 ...prev,
    //                 playerName: player
    //             }));
    //         }
    //     };
    //
    //     // Check initially when the component mounts
    //     checkStoredPlayer();
    // }, [setStoredPlayer]);

    // Trigger stage update based on connection status and storedPlayer changes
    useEffect(() => {
        console.log('CHANGED')
        console.log("connectionStatus" + connectionStatus)
        if (connectionStatus === 'disconnected') {
            setCurrentStage(<LoadingStage attemptConnect={attemptConnect}/>);
        } else if (localPlayerIdValue !== -1) {
            setCurrentStage(<GameStage />);
        } else {
            setCurrentStage(<CharacterSelectStage />);
        }
    }, [connectionStatus, localPlayerIdValue]); // Effect depends on connectionStatus and storedPlayer


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
