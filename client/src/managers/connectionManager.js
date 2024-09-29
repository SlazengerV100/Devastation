import { Stomp } from "@stomp/stompjs";
import {localPlayerId, players} from "../js/atoms.js";
import { store } from '../App'

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

    stompClient.subscribe('/topic/player/move', (message) => {
        updatePlayerPosition(message)
    });

    stompClient.subscribe('/topic/ticket/create', (message) => {
        updateNewTicket(message)
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
                // Update the playerMap with the parsedMessage
                const updatedPlayers = {
                    1: {
                        playerRole: parsedMessage[0].role,
                        direction: parsedMessage[0].direction,
                        x: parsedMessage[0].position.x,
                        y: parsedMessage[0].position.y,
                    },
                    2: {
                        playerRole: parsedMessage[1].role,
                        direction: parsedMessage[1].direction,
                        x: parsedMessage[1].position.x,
                        y: parsedMessage[1].position.y,
                    },
                    3: {
                        playerRole: parsedMessage[2].role,
                        direction: parsedMessage[2].direction,
                        x: parsedMessage[2].position.x,
                        y: parsedMessage[2].position.y,
                    },
                };
                store.set(players, updatedPlayers);

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
    const playerId = store.get(localPlayerId)

    if (playerId === -1){
        console.warn('Cannot send movement: playerID not set');
    }

    console.log("app/player/move -> " + JSON.stringify({ playerId, direction }))
    stompClient.send("/app/player/move", {}, JSON.stringify({ playerId, direction }));
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

const updatePlayerPosition = (message) => {
    try {
        const parsedMessage = JSON.parse(message.body);

        const { id, position, direction } = parsedMessage;
        console.log("Player movement: id: " + id + " position: X: " + position.x + " Y: " + position.y + " direction: " + direction)
        // Update the specific player in playerMap using the ID
        store.set(players, (prev) => ({
            ...prev,
            [id]: {
                ...prev[id], // Keep any existing data for the player
                x: position.x, // Directly update x from position
                y: position.y, // Directly update y from position
                direction, // Update the direction
            },
        }));


    } catch (error) {
        console.error('Failed to parse or update playerMap:', error);
    }
}

const updateNewTicket = (message) => {
    try {
        const parsedMessage = JSON.parse(message.body);
        const { id, position, ticketTitle} = parsedMessage;
        console.log("Ticket received: " + "ID: " + id + " X: " + position.x + " Y: " + position.y + " Title: " + ticketTitle);


    } catch (error){
        console.error('Failed to parse ticket:', error);
    }
}




