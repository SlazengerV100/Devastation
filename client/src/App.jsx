// src/App.js

import React, { useEffect, useState } from 'react';
import Stomp from 'stompjs';
import GameCanvas from './GameCanvas';

const App = () => {
    const [stompClient, setStompClient] = useState(null);
    const [x, setX] = useState(0);
    const [y, setY] = useState(0);

    useEffect(() => {
        // Initialize STOMP client
        const socket = new WebSocket('ws://localhost:8080/stomp-endpoint'); // Replace with your server URL
        const client = Stomp.over(socket);

        client.connect({}, (frame) => {
            console.log('Connected: ' + frame);

            client.subscribe('/topic/state', (stateUpdate) => {
                const state = JSON.parse(stateUpdate.body);
                setX(state.x);
                setY(state.y);
            });

            client.send("/app/getState", {});

            setStompClient(client);
        }, (error) => {
            console.error('Connection error:', error);
        });

        // Cleanup function to disconnect STOMP client
        return () => {
            if (client && client.connected) {
                client.disconnect(() => {
                    console.log('Disconnected');
                });
            }
        };
    }, []);


    // Function to handle key presses
    const handleKeyPress = (event) => {
        if (!stompClient || !stompClient.connected) return;

        const step = 20;
        switch (event.key) {
            case 'ArrowUp':
                setY(prevY => {
                    const newY = prevY - step;
                    stompClient.send("/app/setState", {}, JSON.stringify({ x, y: newY }));
                    return newY;
                });
                break;
            case 'ArrowDown':
                setY(prevY => {
                    const newY = prevY + step;
                    stompClient.send("/app/setState", {}, JSON.stringify({ x, y: newY }));
                    return newY;
                });
                break;
            case 'ArrowLeft':
                setX(prevX => {
                    const newX = prevX - step;
                    stompClient.send("/app/setState", {}, JSON.stringify({ x: newX, y }));
                    return newX;
                });
                break;
            case 'ArrowRight':
                setX(prevX => {
                    const newX = prevX + step;
                    stompClient.send("/app/setState", {}, JSON.stringify({ x: newX, y }));
                    return newX;
                });
                break;
            default:
                break;
        }
    };

    useEffect(() => {
        // Add event listener for keydown events
        window.addEventListener('keydown', handleKeyPress);

        // Cleanup event listener
        return () => {
            window.removeEventListener('keydown', handleKeyPress);
        };
    }, [stompClient, x, y]);

    return (
        <div>
            <GameCanvas state={{ x, y }} />
        </div>
    );
};

export default App;


