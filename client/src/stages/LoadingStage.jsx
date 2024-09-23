import { connect } from '../managers/connectionManager.js'; // Adjust the import path as necessary
import { useAtom } from "jotai";
import {connectionStatusAtom} from '../js/atoms.js'
import {useState, useEffect} from "react";

const LoadingStage = () => {
    const [connectionStatus, setConnectionStatus] = useAtom(connectionStatusAtom)

    //TODO make this appealing this stage is minimal at best right now
    return (
        <div>
            {connectionStatus && <p>Connecting...</p>}
            {connectionStatus === 'disconnected' && (
                <div>
                    <p>Not connected</p>
                    <button onClick={attemptConnect}>Try Reconnecting</button>

                </div>
            )}
            {connectionStatus === 'connected' && <p>Player has connected, swap to the game stage</p>}
        </div>
    );
};

export default LoadingStage;
