import React, { useState, useEffect } from 'react';
import { Stage, Sprite } from '@pixi/react';
import * as PIXI from 'pixi.js';
import { fetchPlayButton } from './js/spriteFrameGrabber.js';
import { FaCheckCircle, FaTimesCircle } from 'react-icons/fa';

// Function to fetch textures
const fetchBackgroundTexture = () => { return PIXI.Texture.from('../assets/background.png'); }
const fetchTitleTexture = () => { return PIXI.Texture.from('../assets/DEV-A-STATION.png'); }

const StyledHomeScreenWithFunctionality = ({ setPlayerTitle, isConnected, tryReconnect, gameState }) => {
    const playerMap = gameState?.playerMap || {};
    const [playButtonTexture, setPlayButtonTexture] = useState(null);
    const [backgroundTexture, setBackgroundTexture] = useState(null);
    const [titleTexture, setTitleTexture] = useState(null);
    const [selectedPlayer, setSelectedPlayer] = useState('');

    useEffect(() => {
        // Fetch textures for button, background, and title
        setPlayButtonTexture(fetchPlayButton());
        setBackgroundTexture(fetchBackgroundTexture());
        setTitleTexture(fetchTitleTexture());
    }, []);

    // Handle mouse hover effects
    const handleMouseOver = (e) => {
        setPlayButtonTexture(fetchPlayButton(true)); // Change tint color to grey on hover
    };

    const handleMouseOut = (e) => {
        setPlayButtonTexture(fetchPlayButton(false)); // Reset tint color
    };

    // Handle click on play button
    const handlePlayButton = () => {
        setPlayerTitle(selectedPlayer);
        console.log('Play button clicked!', selectedPlayer);
    };

    // Handle change in selected player
    const handlePlayerSelection = (title) => {
        setSelectedPlayer(title);
    };

    return (
        <div style={{ position: 'relative', width: '100vw', height: '100vh' }}>
            {isConnected ? (
                // Render PixiJS stage when connected
                <>
                    <Stage
                        options={{ resizeTo: window }}
                        style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%' }}
                    >
                        {backgroundTexture && (
                            <Sprite
                                texture={backgroundTexture}
                                x={0}
                                y={0}
                                width={window.innerWidth}
                                height={window.innerHeight}
                            />
                        )}
                        {titleTexture && (
                            <Sprite
                                texture={titleTexture}
                                x={(window.innerWidth / 2) - (titleTexture.width / 2)}
                                y={100}
                            />
                        )}
                        {playButtonTexture && (
                            <Sprite
                                texture={playButtonTexture}
                                x={(window.innerWidth - playButtonTexture.width) / 2}
                                y={(window.innerHeight - playButtonTexture.height) / 2}
                                interactive
                                buttonMode
                                pointerover={handleMouseOver}
                                pointerout={handleMouseOut}
                                pointertap={handlePlayButton} // onClick functionality
                            />
                        )}
                    </Stage>
                    {/*TODO this is temporary to allow to et past the home screen, but should be removed to a "player select screen"*/}
                    <div style={{ position: 'relative', zIndex: 1, textAlign: 'center', marginTop: '20px' }}>
                        <div style={{ display: 'flex', alignItems: 'center', color: 'green', fontSize: '1.5rem', justifyContent: 'center' }}>
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
                                                alignItems: 'center',
                                                justifyContent: 'center'
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
                    </div>
                </>
            ) : (
                // Show connection error message if not connected
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '100vh' }}>
                    <div style={{ display: 'flex', alignItems: 'center', color: 'red', fontSize: '1.5rem', marginBottom: '20px' }}>
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

export default StyledHomeScreenWithFunctionality;
