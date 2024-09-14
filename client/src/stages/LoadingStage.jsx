import { connect } from '../managers/connectionManager.js'; // Adjust the import path as necessary
import { useAtom } from "jotai";
import {connectionStatusAtom} from '../js/atoms.js'

const LoadingStage = () => {
    const [connectionStatus, setConnectionStatus] = useAtom(connectionStatusAtom)

    const attemptConnect = async () => {
        setConnectionStatus('connecting');
        try {
            await connect();
            setConnectionStatus('connected');
        } catch (error) {
            console.error('Connection failed:', error);
            setConnectionStatus('disconnected');
        }
    };

    //TODO make this appealing this stage is minimal at best right now
    return (
        <div>
            {connectionStatus === 'connecting' && <p>Connecting...</p>}
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
