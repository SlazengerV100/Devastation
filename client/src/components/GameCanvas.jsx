import React, { useEffect, useState, useCallback } from 'react';
import * as PIXI from 'pixi.js';
import { Stage, Graphics, Text } from '@pixi/react';
import Player from "./Sprite.jsx";

const GameCanvas = ({ playerTitle, gameState }) => {
    const [frame, setFrame] = useState(0);
    const [windowWidth, setWindowWidth] = useState(window.innerWidth);
    const [windowHeight, setWindowHeight] = useState(window.innerHeight);
    const [players, setPlayers] = useState([]);
    const [shouldUpdateBackground, setShouldUpdateBackground] = useState(true);
    const [resizeTimeout, setResizeTimeout] = useState(null);

    useEffect(() => {
        let animationFrameId;
        const animate = () => {
            setFrame(prev => prev + 1);
            animationFrameId = requestAnimationFrame(animate);
        };

        animationFrameId = requestAnimationFrame(animate);

        return () => cancelAnimationFrame(animationFrameId);
    }, []);

    useEffect(() => {
        // Parse the gameState and update the player positions
        if (gameState && gameState.playerMap) {
            const playerArray = Object.entries(gameState.playerMap).map(([name, data]) => ({
                name,
                role: data.role,
                x: data.x,
                y: data.y,
                active: data.active
            }));
            setPlayers(playerArray);
        }
    }, [gameState]);

    // Handle window resize
    const handleResize = useCallback(() => {
        if (resizeTimeout) {
            clearTimeout(resizeTimeout);
        }
        const timeout = setTimeout(() => {
            setWindowWidth(window.innerWidth);
            setWindowHeight(window.innerHeight);
            setShouldUpdateBackground(true); // Flag to update background on resize
        }, 200);  // Adjust delay as needed
        setResizeTimeout(timeout);
    }, [resizeTimeout]);

    useEffect(() => {
        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
            if (resizeTimeout) {
                clearTimeout(resizeTimeout); // Clean up timeout on unmount
            }
        };
    }, [handleResize, resizeTimeout]);

    // Create a graphics object to draw the background
    const drawBackground = (g) => {
        if (shouldUpdateBackground) {
            g.clear();
            g.beginFill(0xf4f3ef); // Set the background color
            g.drawRect(0, 0, windowWidth, windowHeight);
            g.endFill();
            setShouldUpdateBackground(false); // Prevent further updates until needed
        }
    };

    return (
        <Stage width={windowWidth} height={windowHeight - 7}>
            <Graphics draw={drawBackground} zIndex={-1} />
            <Text
                text={`You are ${playerTitle}`}
                x={windowWidth / 2} // Center horizontally
                y={20} // Position near the top
                anchor={0.5} // Center the text based on its position
                style={new PIXI.TextStyle({
                    fontFamily: 'Arial',
                    fontSize: 24,
                    fill: '#000000', // Text color
                    align: 'center',
                })}
            />
            {players.map((p, i) => <Player key={i} player={p} frame={frame} />)}
        </Stage>
    );
};

export default GameCanvas;
