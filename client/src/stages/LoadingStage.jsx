import { connect } from '../managers/connectionManager.js'; // Adjust the import path as necessary
import { useAtom } from "jotai";
import {connectionStatusAtom} from '../js/atoms.js'
import {useState} from "react";

const LoadingStage = () => {
    const [connectionStatus, setConnectionStatus] = useAtom(connectionStatusAtom)

    const [connecting, setConnecting] = useState(false)

    const attemptConnect = async () => {
        setConnecting(true)
        try {
            await connect();
            setConnectionStatus('connected');
            setConnecting(false)
        } catch (error) {
            console.error('Connection failed:', error);
            setConnectionStatus('disconnected');
            setConnecting(false)
        }
    };

    //TODO make this appealing this stage is minimal at best right now
    return (
        <div>
            {connecting && <p>Connecting...</p>}
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
