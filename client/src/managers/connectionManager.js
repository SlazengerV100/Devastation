import { Client } from '@stomp/stompjs';

// WebSocket and Stomp setup
const socket = new WebSocket('ws://localhost:8080/stomp-endpoint');
const client = new Client({ webSocketFactory: () => socket });

let isConnected = false; // Flag to track connection status

// Exported function to establish the connection
const connect = (onConnected) => {
    client.onConnect = () => {
        isConnected = true;
        console.log('Connected');

        // Subscribe to the state topic after the connection is established
        client.subscribe('/topic/state', (stateUpdate) => {
            const state = JSON.parse(stateUpdate.body);
            console.log('Received state: ', state);
        });

        // Call the onConnected callback if provided
        if (onConnected) {
            onConnected();
        }
    };

    client.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    client.activate(); // Activates the STOMP client and starts the connection process
};

// Function to trigger a game state update
const triggerGameStateUpdate = function () {
    if (isConnected) {
        client.publish({ destination: '/app/getState' });
    } else {
        console.warn('Cannot trigger game state update. Not connected to WebSocket.');
    }
};

export { connect, triggerGameStateUpdate };
