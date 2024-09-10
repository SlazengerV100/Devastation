import React, { useEffect, useState } from 'react';
import * as PIXI from 'pixi.js';
import { Stage, Container, Sprite, Graphics, Text } from '@pixi/react';
import GameBoard from "./GameBoard.jsx";

const GameCanvas = ({ playerTitle, gameState, gameBoard }) => {
    const spriteSize = 40;

    const playerImages = {
        "PROJECT_MANAGER": 'https://static.vecteezy.com/system/resources/previews/028/652/011/original/pixel-art-student-character-png.png',
        "TESTER": 'https://static.vecteezy.com/system/resources/previews/027/190/731/original/pixel-art-black-t-shirt-man-character-png.png',
        "DEVELOPER": 'https://static.vecteezy.com/system/resources/previews/027/190/803/original/pixel-art-female-teacher-character-png.png',
    };

    const [windowWidth, setWindowWidth] = useState(window.innerWidth);
    const [windowHeight, setWindowHeight] = useState(window.innerHeight);
    const [players, setPlayers] = useState([]);

    useEffect(() => {
        if (gameState && gameState.playerMap) {
            const playerArray = Object.entries(gameState.playerMap).map(([name, data]) => ({
                name,
                role: data.role,
                x: data.x * spriteSize, // Adjusting based on sprite size
                y: data.y * spriteSize,
                active: data.active
            }));
            setPlayers(playerArray);
        }
    }, [gameState, spriteSize]);

    useEffect(() => {
        const handleResize = () => {
            setWindowWidth(window.innerWidth);
            setWindowHeight(window.innerHeight);
        };

        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    const drawBackground = (g) => {
        g.clear();
        g.beginFill(0xf4f3ef);
        g.drawRect(0, 0, windowWidth, windowHeight);
        g.endFill();
    };

    // Calculate the total width and height of the gameBoard
    const boardWidth = gameBoard[0].length * spriteSize;
    const boardHeight = gameBoard.length * spriteSize;

    // Calculate the offset to center the gameBoard
    const offsetX = (windowWidth - boardWidth) / 2;
    const offsetY = (windowHeight - boardHeight) / 2;

    return (
        <Stage width={windowWidth} height={windowHeight - 7}>
            <Graphics draw={drawBackground} zIndex={-2} />

            <Text
                text={`You are ${playerTitle}`}
                x={windowWidth / 2}
                y={20}
                anchor={0.5}
                style={new PIXI.TextStyle({
                    fontFamily: 'Arial',
                    fontSize: 24,
                    fill: '#000000',
                    align: 'center',
                })}
            />

            {/* Render GameBoard */}
            <Container x={offsetX} y={offsetY}>
                <GameBoard gameBoard={gameBoard} spriteSize={spriteSize} />

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
            </Container>


        </Stage>
    );
};

export default GameCanvas;


