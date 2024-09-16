import React, { useEffect } from 'react';
import { connect, triggerGameStateUpdate } from './managers/connectionManager';

function App() {
    useEffect(() => {
        // Establish the WebSocket connection and perform actions on connection
        connect(() => {
            // This function is called only after a successful connection
            triggerGameStateUpdate();
        });

    }, []);

    return (
        <div>
            <h1>My App</h1>
        </div>
    );
}

export default App;
