import { Stomp } from "@stomp/stompjs";
import { useAtom } from "jotai";
import { localCharacterAtom } from "../js/atoms.js";
import useSetSate from "../hooks/useSetSate.js";

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
    const setState = useSetSate();

    stompClient.subscribe('/topic/greetings', (message) => {
        console.log('Received greeting:', message.body);
    });

    stompClient.subscribe('/topic/state', (message) => {
        try {
            const state = JSON.parse(message.body);
            console.log('Received state:', state);
            setState(state);
        } catch (error) {
            console.error('Failed to parse state:', error);
        }
    });
};

export const requestState = () => {
    if (stompClient) {
        stompClient.send('/app/getState');
    } else {
        console.warn('Cannot request state: not connected.');
    }
};

export const sendPlayerMovement = (name, direction) => {
    if (stompClient) {
        stompClient.send("/app/movePlayer", {}, JSON.stringify({ name, direction }));
    } else {
        console.warn('Cannot send movement: not connected.');
    }
};


