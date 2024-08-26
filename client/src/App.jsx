// src/App.js

import React, { useEffect, useState } from 'react';
import Stomp from 'stompjs';
import GameCanvas from './GameCanvas';
import HomeScreen from "./HomeScreen.jsx";

const App = () => {
    const [stompClient, setStompClient] = useState(null);
    const [isConnected, setConnected] = useState(false);
    const [gameStarted, setGameStarted] = useState(false);
    const [playerTitle, setPlayerTitle] = useState(null);
    const [gameState, setGameState] = useState(null);

    const connect = () => {
        // Initialize STOMP client
        const socket = new WebSocket('ws://localhost:8080/stomp-endpoint'); // Replace with your server URL
        const client = Stomp.over(socket);

        client.connect({}, (frame) => {
            console.log('Connected: ' + frame);

            setConnected(true);
            client.subscribe('/topic/state', (stateUpdate) => {
                const state = JSON.parse(stateUpdate.body);
                setGameState(state);
            });
            client.send("/app/getState", {});
            setStompClient(client);
        }, (error) => {
            console.error('Connection error:', error);
        });
    }

    const disconnect = () => {
        if (stompClient && stompClient.connected) {
            stompClient.disconnect(() => {
                console.log('Disconnected');
                setConnected(false);
            });
        }
    }

    useEffect(() => {
        if (localStorage.getItem('playerTitle')){
            setGameStarted(true);
            setPlayerTitle(localStorage.getItem('playerTitle'))
        }
    }, []);

    // Connect to socket when component mounts and disconnect when dismounts
    useEffect(() => {
        connect();
        return () => {
            disconnect();
        };
    }, []);

    // Init event listeners
    useEffect(() => {
        // Add event listener for keydown events
        window.addEventListener('keydown', handleKeyPress);

        // Cleanup event listener
        return () => {
            window.removeEventListener('keydown', handleKeyPress);
        };
    }, []);

    // set client player title into local storage
    useEffect(() => {
        if (playerTitle) {
            localStorage.setItem('playerTitle', playerTitle);
        }
    }, [playerTitle]);


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

    return (
        <div>
            {
                !gameStarted
                    ?
                    <HomeScreen setPlayerTitle={setPlayerTitle} isConnected={isConnected} tryReconnect={connect} gameState={gameState} setGameStarted={setGameStarted}/>
                    :
                    <GameCanvas playerTitle={playerTitle}/>
            }


        </div>
    );
};

export default App;


