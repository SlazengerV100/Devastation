import React, { useEffect, useState, useRef, useCallback } from 'react';
import Stomp from 'stompjs';
import GameCanvas from './GameCanvas';
import HomeScreen from "./HomeScreen.jsx";

const App = () => {
    const [isConnected, setConnected] = useState(false);
    const [gameStarted, setGameStarted] = useState(false);
    const [playerTitle, setPlayerTitle] = useState(null);
    const [gameState, setGameState] = useState(null);

    const stompClientRef = useRef(null); // Using useRef to store stompClient

    useEffect(() => {
        connect();
    }, [])
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

    const disconnect = () => {
        if (stompClientRef.current && stompClientRef.current.connected) {
            stompClientRef.current.disconnect(() => {
                console.log('Disconnected');
                setConnected(false);
                stompClientRef.current = null; // Clear stompClientRef on disconnect
            });
        }
    }

    useEffect(() => {
        const handleKeyPress = (event) => {
            const client = stompClientRef.current;
            if (!client || !client.connected) {
                console.warn('STOMP client is not connected.');
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

    useEffect(() => {
        if (sessionStorage.getItem('playerTitle')) {
            setPlayerTitle(sessionStorage.getItem('playerTitle'));
            setGameStarted(true);
        }
    }, []);

    useEffect(() => {
        console.log("Player Title: " + playerTitle);
        console.log("stompClientRef: " + stompClientRef.current);
        if (playerTitle && stompClientRef.current && stompClientRef.current.connected) {
            console.log("Set player name");
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
                    <HomeScreen setPlayerTitle={setPlayerTitle} isConnected={isConnected} tryReconnect={connect} gameState={gameState} />
                    :
                    <GameCanvas playerTitle={playerTitle} />
            }
        </div>
    );
};

export default App;



