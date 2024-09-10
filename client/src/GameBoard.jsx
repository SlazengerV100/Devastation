import React, { useEffect, useState } from 'react';
import { Container, Sprite } from '@pixi/react';

// eslint-disable-next-line react/prop-types
const GameBoard = ({ gameBoard, spriteSize }) => {
    const tileImages = {
        "WALL": 'wall-tile.png',
        "PLANNING": 'planning-tile.png',
        "DEVELOPMENT": 'development-tile.png',
        "TESTING": 'testing-tile.png',
        "EMPTY": 'free-tile.png',
    };

    const [tiles, setTiles] = useState([]);

    useEffect(() => {
        if (gameBoard) {
            // Parse the gameBoard to create the tile sprites
            const tileArray = [];

            gameBoard.forEach((row, rowIndex) => {
                row.forEach((tile, colIndex) => {
                    const tileImage = tileImages[tile.room];
                    tileArray.push({
                        x: colIndex * spriteSize,
                        y: rowIndex * spriteSize,
                        image: tileImage,
                    });
                });
            });

            setTiles(tileArray);
        }
    }, [gameBoard, spriteSize]);

    return (
        <Container>
            {tiles.map((tile, index) => (
                <Sprite
                    key={index}
                    image={"../assets/tiles/" + tile.image}
                    x={tile.x}
                    y={tile.y}
                    width={spriteSize}
                    height={spriteSize}
                />
            ))}
        </Container>
    );
};

export default GameBoard;
