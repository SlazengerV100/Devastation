import React, { useEffect, useState } from 'react';
import * as PIXI from 'pixi.js';
import { Stage, Container, Sprite, Graphics , Text} from '@pixi/react';
import Player from "./Sprite.jsx";

const GameCanvas = ({ playerTitle, gameState }) => {
    const [frame, setFrame] = useState(0);
    const [windowWidth, setWindowWidth] = useState(window.innerWidth);
    const [windowHeight, setWindowHeight] = useState(window.innerHeight);

    const [players, setPlayers] = useState([]);

    // This is to increment the client side frame
    useEffect(() => {
        const interval = setInterval(() => {
            setFrame(prev => prev + 1)
            console.log(frame)
        }, 50)
        //clean up function
        return () => clearInterval(interval)
    }, [])

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

    useEffect(() => {
        // Handle window resize
        const handleResize = () => {
            setWindowWidth(window.innerWidth);
            setWindowHeight(window.innerHeight);
        };

        window.addEventListener('resize', handleResize);

        // Cleanup event listener on component unmount
        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    // Create a graphics object to draw the background
    const drawBackground = (g) => {
        g.clear();
        g.beginFill(0xf4f3ef); // Set the background color
        g.drawRect(0, 0, windowWidth, windowHeight);
        g.endFill();
    };

    return (
        <Stage width={windowWidth} height={windowHeight - 7}>
            <Graphics
                draw={drawBackground}
                zIndex={-1} // Ensure the background is behind other elements
            />
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
            {players.map((p, i) => (<Player key={i} player={p}/>))}
        </Stage>
    );
};

export default GameCanvas;

