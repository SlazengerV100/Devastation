import { Stomp } from "@stomp/stompjs";
import {localHeldTicket, localPlayerId, players, ticketsAtom} from "../js/atoms.js";
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

    stompClient.subscribe('/topic/player/ticket/pickUp', (message) => {
        updateTicketPickUp(message)
    })

    stompClient.subscribe('/topic/player/ticket/drop', (message) => {
        updateTicketDrop(message)
    })
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

export const sendPlayerAction = (actionType) => {
    if (!stompClient){
        console.warn('Cannot send movement: not connected.');
        return
    }

    const playerId = store.get(localPlayerId)

    switch (actionType){
        case 'PICKUP':
            stompClient.send("/app/player/ticket/pickUp", {}, JSON.stringify({playerId}));
            break;
        case 'DROP':
            stompClient.send("/app/player/ticket/drop", {}, JSON.stringify({playerId}))
            break;
        default:
            console.error("Action type not recognised")
    }


}

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

        store.set(players, (prev) => ({
            ...prev,
            [id]: {
                ...prev[id],
                x: position.x,
                y: position.y,
                direction,
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

        store.set(ticketsAtom, (prevTickets) => ({
            ...prevTickets,
            [id]: { id: id, x: position.x, y: position.y, title: ticketTitle, held: false },
        }));

    } catch (error){
        console.error('Failed to parse ticket:', error);
    }
}

const updateTicketPickUp = (message) => {
    try {
        const parsedMessage = JSON.parse(message.body);
        const { id, heldTicket } = parsedMessage;

        if (heldTicket === null) {
            console.log("No ticket to pick up");
            return;
        }

        console.log("Ticket pick up: " + " Player ID: " + id + " Held ticket: " + heldTicket.ticketTitle);

        const ticketHeldId = heldTicket.id;

        // Remove the held ticket from the ticketsAtom
        store.set(ticketsAtom, (prevTickets) => {
            const { [ticketHeldId]: removedTicket, ...remainingTickets } = prevTickets; // Destructure to remove the held ticket
            return remainingTickets;
        });

        // Update the localHeldTicket for the local player if the ID matches
        if (id === store.get(localPlayerId)) {
            console.log("HELD TICKET TO STORE: " + JSON.stringify(heldTicket));
            store.set(localHeldTicket, heldTicket);
        } else {
            if (store.get(localHeldTicket)?.id === ticketHeldId) {
                store.set(localHeldTicket, null);
            }
        }

        console.log("Updated localHeldTicket:", store.get(localHeldTicket));

    } catch (error) {
        console.error('Failed to parse player that attempted to pick up ticket:', error);
    }
};

const updateTicketDrop = (message) => {
    try {
        const ticket = JSON.parse(message.body);
        console.log("Ticket dropped: " + " Ticket ID: " + ticket);

        if (ticket.id === store.get(localHeldTicket)?.id) {
            store.set(localHeldTicket, null);
        }

    } catch (error) {
        console.error('Failed to parse ticket drop message:', error);
    }
};





