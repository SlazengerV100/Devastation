import React, { useEffect, useState, useRef } from 'react';
import Stomp from 'stompjs';
import GameCanvas from './GameCanvas';
import StyledHomeScreen from "./StyledHomeScreen.jsx";

const App = () => {
    const [isConnected, setConnected] = useState(false);
    const [gameStarted, setGameStarted] = useState(false);
    const [playerTitle, setPlayerTitle] = useState(null);
    const [gameState, setGameState] = useState(null);

    const stompClientRef = useRef(null); // Using useRef to store stompClient

    // Attempt to connect to WebSocket upon mounting
    useEffect(() => {
        connect();
    }, [])

    // Upon mounting, check if playerTitle has previously been set
    useEffect(() => {
        if (sessionStorage.getItem('playerTitle')) {
            setPlayerTitle(sessionStorage.getItem('playerTitle'));
            setGameStarted(true);
        }
    }, []);

    // Connect to WebSocket
    const connect = () => {
        const socket = new WebSocket('ws://localhost:8080/stomp-endpoint'); // Replace with your server URL
        const client = Stomp.over(socket);

        client.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            setConnected(true);
            stompClientRef.current = client; // Update stompClientRef with the connected client
            client.subscribe('/topic/state', (stateUpdate) => {
                const state = JSON.parse(stateUpdate.body);
                setGameState(state);
            });
            client.send("/app/getState", {});
        }, (error) => {
            console.error('Connection error:', error);
            setConnected(false);
        });
    }

    // Disconnect from WebSocket
    const disconnect = () => {
        if (stompClientRef.current && stompClientRef.current.connected) {
            stompClientRef.current.disconnect(() => {
                console.log('Disconnected');
                setConnected(false);
                stompClientRef.current = null; // Clear stompClientRef on disconnect
            });
        }
    }

    // Set up key press listening and handling of key presses for player movement
    useEffect(() => {
        const handleKeyPress = (event) => {
            const client = stompClientRef.current;
            if (!client || !client.connected) {
                console.warn('client is not connected.');
                return;
            }

            let direction;
            switch (event.key) {
                case 'ArrowUp':
                    direction = "UP";
                    break;
                case 'ArrowDown':
                    direction = "DOWN";
                    break;
                case 'ArrowLeft':
                    direction = "LEFT";
                    break;
                case 'ArrowRight':
                    direction = "RIGHT";
                    break;
                default:
                    return; // No action needed for other keys
            }

            if (playerTitle) {
                client.send("/app/movePlayer", {}, JSON.stringify({ playerTitle, direction }));
            } else if (sessionStorage.getItem('playerTitle')) {
                setPlayerTitle(sessionStorage.getItem('playerTitle'))
                client.send("/app/movePlayer", {}, JSON.stringify({ playerTitle, direction }));
            } else {
                console.warn('Player title is not set.');
            }
        };

        window.addEventListener('keydown', handleKeyPress);

    }, [playerTitle]);

    // Set player title when it is set in the HomeScreen component and start game
    useEffect(() => {
        if (playerTitle && stompClientRef.current && stompClientRef.current.connected) {
            sessionStorage.setItem('playerTitle', playerTitle);
            const activate = true;
            stompClientRef.current.send("/app/activatePlayer", {}, JSON.stringify({ playerTitle, activate }));
            setGameStarted(true);
        }
    }, [playerTitle]);

    return (
        <div>
            {
                !gameStarted
                    ?
                    <StyledHomeScreen gameState={gameState} isConnected={isConnected} setPlayerTitle={setPlayerTitle} tryReconnect={connect}/>
                    :
                    <GameCanvas playerTitle={playerTitle} gameState={gameState}/>
            }
        </div>
    );
};

export default App;



