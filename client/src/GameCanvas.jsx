// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from 'react';
import * as PIXI from 'pixi.js';
import { Stage, Container, Sprite, Graphics , Text} from '@pixi/react';

// eslint-disable-next-line react/prop-types
const GameCanvas = ({ playerTitle, gameState, gameBoard }) => {
    const spriteSize = 80;

    const playerImages = {
        "PROJECT_MANAGER": 'https://static.vecteezy.com/system/resources/previews/028/652/011/original/pixel-art-student-character-png.png',
        "TESTER": 'https://static.vecteezy.com/system/resources/previews/027/190/731/original/pixel-art-black-t-shirt-man-character-png.png',
        "DEVELOPER": 'https://static.vecteezy.com/system/resources/previews/027/190/803/original/pixel-art-female-teacher-character-png.png',
    };

    const [windowWidth, setWindowWidth] = useState(window.innerWidth);
    const [windowHeight, setWindowHeight] = useState(window.innerHeight);

    const [players, setPlayers] = useState([]);

    useEffect(() => {
        // Parse the gameState and update the player positions
        if (gameState && gameState.playerMap) {
            // eslint-disable-next-line react/prop-types
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
        console.log(gameBoard)
    }, [gameBoard])

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
            {players.map(player => (
                <Container key={player.name}>
                    <Sprite
                        image={playerImages[player.role]}
                        x={player.x}
                        y={player.y}
                        width={spriteSize}
                        height={spriteSize}
                    />
                    <Text
                        text={player.name}
                        x={player.x}
                        y={player.y + spriteSize + 5}
                        style={{
                            fontFamily: 'Arial',
                            fontSize: 14,
                            fill: '#000000',
                            align: 'center',
                        }}
                    />
                </Container>
            ))}
        </Stage>
    );
};

export default GameCanvas;

