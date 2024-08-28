import React, { useState } from 'react';
import { FaCheckCircle, FaTimesCircle } from 'react-icons/fa';

const HomeScreen = ({ setPlayerTitle, isConnected, tryReconnect, gameState }) => {
    // Use default empty object if gameState is null
    const playerMap = gameState?.playerMap || {};
    const [selectedPlayer, setSelectedPlayer] = useState('');

    // Handle change in selected player
    const handlePlayerSelection = (title) => {
        setSelectedPlayer(title);
    };

    const handlePlayButton = () => {
        setPlayerTitle(selectedPlayer);
    }

    return (
        <div
            style={{
                width: '100vw',
                height: '100vh',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                padding: '20px',
                textAlign: 'center' // Ensures that the text inside the container is centered
            }}
        >
            <h1 style={{ fontSize: '3rem', marginBottom: '20px' }}>Dev-A-Station</h1>

            {isConnected ? (
                <>
                    <div style={{ display: 'flex', alignItems: 'center', color: 'green', fontSize: '1.5rem' }}>
                        <FaCheckCircle style={{ marginRight: '10px' }} />
                        Connected to WebSocket
                    </div>
                    <div style={{ marginBottom: '20px' }}>
                        <h2>Select a Player:</h2>
                        <ul style={{ listStyleType: 'none', padding: 0 }}>
                            {Object.keys(playerMap).map(playerTitle => {
                                const player = playerMap[playerTitle];
                                const isActive = player.active;

                                return (
                                    <li
                                        key={playerTitle}
                                        style={{
                                            marginBottom: '10px',
                                            opacity: isActive ? 0.5 : 1,
                                            cursor: isActive ? 'not-allowed' : 'pointer',
                                            display: 'flex',
                                            alignItems: 'center'
                                        }}
                                    >
                                        <input
                                            type="radio"
                                            name="player"
                                            value={playerTitle}
                                            disabled={isActive}
                                            checked={selectedPlayer === playerTitle}
                                            onChange={() => handlePlayerSelection(playerTitle)}
                                        />
                                        <span style={{ marginLeft: '10px' }}>{playerTitle}</span>
                                    </li>
                                );
                            })}
                        </ul>
                        <button
                            onClick={handlePlayButton}
                            style={{
                                padding: '10px 20px',
                                fontSize: '1rem',
                                cursor: 'pointer',
                                backgroundColor: '#28a745',
                                color: '#FFF',
                                border: 'none',
                                borderRadius: '5px',
                                marginTop: '20px',
                                visibility: selectedPlayer ? 'visible' : 'hidden'
                            }}
                        >
                            Play
                        </button>
                    </div>
                </>
            ) : (
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', color: 'red', fontSize: '1.5rem' }}>
                    <div style={{ display: 'flex', alignItems: 'center', marginBottom: '20px' }}>
                        <FaTimesCircle style={{ marginRight: '10px' }} />
                        Not connected to WebSocket
                    </div>
                    <button
                        onClick={tryReconnect}
                        style={{
                            padding: '10px 20px',
                            fontSize: '1rem',
                            cursor: 'pointer',
                            backgroundColor: '#007BFF',
                            color: '#FFF',
                            border: 'none',
                            borderRadius: '5px'
                        }}
                    >
                        Try Again
                    </button>
                </div>
            )}
        </div>
    );
};

export default HomeScreen;

