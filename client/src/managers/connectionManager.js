import { Stomp } from "@stomp/stompjs";
import { localCharacterAtom } from '../js/atoms.js';
import { store } from '../App';

let stompClient;

export const connect = async () => {
    console.log("start");
    return new Promise((resolve, reject) => {
        const socket = new WebSocket('ws://localhost:8080/stomp-endpoint');

        socket.addEventListener('error', (error) => {
            console.error('WebSocket error:', error);
            reject(new Error('WebSocket connection failed'));
        });

        stompClient = Stomp.over(socket);

        // Handle STOMP connection errors
        stompClient.connect({}, (frame) => {
            console.log('Connected:', frame);
            setupSubscriptions();
            requestState();
            resolve();
        }, (error) => {
            console.error('STOMP connection error:', error);
            stompClient = null;
            reject(new Error('STOMP connection failed'));
        });
    });
};

const setupSubscriptions = () => {
    if (!stompClient) return;

    stompClient.subscribe('/topic/greetings', (message) => {
        console.log('Received greeting:', message.body);
    });

};

export const requestState = async () => {
    if (!stompClient) {
        console.warn('Cannot request state: not connected.');
        return;
    }
    stompClient.send('/app/players');

    // Return a Promise that resolves from server response
    return new Promise((resolve, reject) => {
        const subscription = stompClient.subscribe('/topic/players', (message) => {
            try {
                const parsedMessage = JSON.parse(message.body);
                console.log(parsedMessage)
                resolve(parsedMessage); // Resolve the Promise with the parsed message
            } catch (error) {
                reject('Failed to parse message');
            }

            // memory leak avoidance
            subscription.unsubscribe();
        });
    });
};

export const sendPlayerMovement = (direction) => {
    if (!stompClient){
        console.warn('Cannot send movement: not connected.');
        return
    }
    name = sessionStorage.getItem("playerID");
    if (name == null){
        console.warn('Cannot send movement: playerID not set');
    }

    console.log("/app/movePlayer -> " + JSON.stringify({ role: name, direction }))
    stompClient.send("/app/movePlayer", {}, JSON.stringify({ role: name, direction }));

};

export const activatePlayer = async (playerId, activate = true) => {
    if (!stompClient) {
        console.warn('Cannot activate player: not connected.');
        return;
    }
    console.log(playerId)
    // Send the activation request
    stompClient.send("/app/player/activate", {}, JSON.stringify({ playerId, activate }));

    // Return a Promise that resolves when the server responds
    return new Promise((resolve, reject) => {
        const subscription = stompClient.subscribe('/topic/player/activate', (message) => {
            try {
                const parsedMessage = JSON.parse(message.body);
                console.log("PLAYER IS NOW: " + parsedMessage)
                resolve(parsedMessage); // Resolve the Promise with the updated player map
            } catch (error) {
                reject('Failed to parse activation response');
            }

            // Unsubscribe to avoid memory leaks
            subscription.unsubscribe();
        });
    });
};




