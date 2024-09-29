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
    const [loading, setLoading] = useState(true);

    // Initial useEffect for asset initialization and connection attempt
    useEffect(() => {
        const initialize = async () => {
            setLoading(true); // Start loading

            try {
                // Initialize assets
                await init();

                // Attempt connection
                await attemptConnect();
                console.log("Connection established!");

                // Check for playerID in session storage and set if exists
                const storedPlayerID = sessionStorage.getItem("playerID");
                if (storedPlayerID) {
                    setLocalPlayerIdValue(Number(storedPlayerID));
                }
            } catch (error) {
                console.error("Error during initialization or connection:", error);
            } finally {
                setLoading(false); // Stop loading after everything is done
            }
        };
        initialize();
    }, []); // Dependency array left empty to run only once on mount


    const attemptConnect = async () => {
        try {
            await connect();
            setConnectionStatus('connected');
        } catch (error) {
            console.error('Connection failed:', error);
            setConnectionStatus('disconnected');
        }
    };

    // Trigger stage update based on connection status and storedPlayer changes
    useEffect(() => {
        if (connectionStatus === 'disconnected') {
            setCurrentStage(<LoadingStage attemptConnect={attemptConnect}/>);
        }

        else if (localPlayerIdValue !== -1) {
            setCurrentStage(<GameStage />);
        }

        else {
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
