import { useState, useEffect } from 'react';
import Stomp from 'stompjs'; // Import Stomp library

const SOCKET_URL = 'ws://localhost:8080/stomp-endpoint'; // Replace with your WebSocket URL

const useWebSocket = () => {
    const [currentX, setCurrentX] = useState(0);
    const [currentY, setCurrentY] = useState(0);
    const [newX, setNewX] = useState(0);
    const [newY, setNewY] = useState(0);
    const [stompClient, setStompClient] = useState(null);

    const ballSize = 50;
    const canvasSize = 500;

    useEffect(() => {
        // Cleanup function to disconnect WebSocket on component unmount
        return () => {
            if (stompClient !== null) {
                stompClient.disconnect(() => console.log("Disconnected"));
            }
        };
    }, [stompClient]);

    const connect = () => {
        const socket = new WebSocket(SOCKET_URL);
        const client = Stomp.over(socket);

        client.connect({}, (frame) => {
            console.log('Connected: ' + frame);

            client.subscribe('/topic/greetings', (greeting) => {
                showGreeting(JSON.parse(greeting.body));
            });

            client.subscribe('/topic/state', (stateUpdate) => {
                const state = JSON.parse(stateUpdate.body);
                //showState(state);
            });

            requestState(client);
        }, (error) => {
            console.error('Connection error: ', error);
            return null;
        });
        setIsC
        return client;
    };

    const disconnect = (stompClient) => {
        if (stompClient !== null) {
            stompClient.disconnect(() => console.log("Disconnected"));
        }
    };

    const updateState = (stompClient, x, y) => {
        if (stompClient !== null) {
            stompClient.send("/app/setState", {}, JSON.stringify({ x, y }));
        }
    };

    const requestState = (client) => {
        client.send("/app/players", {});
    };

    const showState = (state) => {
        setCurrentX(state.x);
        setCurrentY(state.y);
    };


    return {
        connect,
        disconnect,
        updateState,
    };
};

export default useWebSocket;
