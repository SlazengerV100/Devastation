import { useEffect, useState } from 'react';
import { connect, disconnect } from '../managers/connectionManager.js'; // Adjust the import path as necessary

const LoadingStage = () => {
    const [gameState, setGameState] = useState(null);
    const [connectionStatus, setConnectionStatus] = useState('disconnected'); // can be 'disconnected', 'connecting', 'connected'
    const [errorMessage, setErrorMessage] = useState(''); // For displaying errors

    // Define the state update handler
    const handleStateUpdate = (state) => {
        setGameState(state);
    };

    // Connect to the WebSocket server
    const attemptConnect = async () => {
        setConnectionStatus('connecting');
        setErrorMessage('');
        try {
            await connect(handleStateUpdate);
            setConnectionStatus('connected');
        } catch (error) {
            console.error('Connection failed:', error);
            setConnectionStatus('disconnected');
            setErrorMessage('Failed to connect: ' + error.message);
        }
    };

    return (
        <div>
            {connectionStatus === 'connecting' && <p>Connecting...</p>}
            {connectionStatus === 'disconnected' && (
                <div>
                    <p>Not connected</p>
                    {errorMessage && <p>Error: {errorMessage}</p>}
                    <button onClick={attemptConnect}>Try Reconnecting</button>
                </div>
            )}
            {connectionStatus === 'connected' && <p>Loading game state...</p>}
            {gameState && <pre>{JSON.stringify(gameState, null, 2)}</pre>}
        </div>
    );
};

export default LoadingStage;
