import React, { useEffect, useState, useRef, useCallback } from 'react';
import Stomp from 'stompjs';
import GameCanvas from './GameCanvas.jsx';
import HomeScreen from "./HomeScreen.jsx";

const App = () => {
    const [isConnected, setConnected] = useState(false);
    const [gameStarted, setGameStarted] = useState(false);
    const [playerTitle, setPlayerTitle] = useState(null);
    const [gameState, setGameState] = useState(null);

    const stompClientRef = useRef(null);

    useEffect(() => {
        connect();
        return () => disconnect();
    }, []);

    useEffect(() => {
        if (sessionStorage.getItem('playerTitle')) {
            setPlayerTitle(sessionStorage.getItem('playerTitle'));
            setGameStarted(true);
        }
    }, []);

    const connect = () => {
        const socket = new WebSocket('ws://localhost:8080/stomp-endpoint');
        const client = Stomp.over(socket);

        client.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            setConnected(true);
            stompClientRef.current = client;
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
                stompClientRef.current = null;
            });
        }
    }

    const handleKeyPress = useCallback((event) => {
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
                return;
        }

        const currentTitle = playerTitle || sessionStorage.getItem('playerTitle');
        if (currentTitle) {
            client.send("/app/movePlayer", {}, JSON.stringify({ playerTitle: currentTitle, direction }));
        } else {
            console.warn('Player title is not set.');
        }
    }, [playerTitle]);

    useEffect(() => {
        window.addEventListener('keydown', handleKeyPress);
        return () => {
            window.removeEventListener('keydown', handleKeyPress);
        };
    }, [handleKeyPress]);

    useEffect(() => {
        if (playerTitle && stompClientRef.current && stompClientRef.current.connected) {
            sessionStorage.setItem('playerTitle', playerTitle);
            stompClientRef.current.send("/app/activatePlayer", {}, JSON.stringify({ playerTitle, activate: true }));
            setGameStarted(true);
        }
    }, [playerTitle]);

    return (
        <div>
            {!gameStarted
                ? <HomeScreen setPlayerTitle={setPlayerTitle} isConnected={isConnected} tryReconnect={connect} gameState={gameState} />
                : <GameCanvas playerTitle={playerTitle} gameState={gameState} />
            }
        </div>
    );
};

export default App;
