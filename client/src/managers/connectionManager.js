const Stomp = require('@stomp/stompjs');
const socket = new WebSocket('ws://localhost:8080/stomp-endpoint');
const client = Stomp.client(socket);

let isConnected = false;

// Exported function to establish the connection
const connect = (onConnected) => {
    client.connect({}, () => {
        isConnected = true;
        console.log('Connected');

        // Subscribe to the state topic after the connection is established
        client.subscribe('/topic/state', (stateUpdate) => {
            const state = JSON.parse(stateUpdate.body);
            console.log("Received state: ");
            console.log(state);
        });

        // Call the onConnected callback if provided
        if (onConnected) {
            onConnected();
        }
    }, errorCallback);
};

// Callback for connection errors
const errorCallback = function (error) {
    console.error(error.headers.message);
    alert(error.headers.message);
};

// Function to trigger a game state update
const triggerGameStateUpdate = function () {
    if (isConnected) {
        client.send("/app/getState", {});
    } else {
        console.warn('Cannot trigger game state update. Not connected to WebSocket.');
    }
};

export { connect, triggerGameStateUpdate };

