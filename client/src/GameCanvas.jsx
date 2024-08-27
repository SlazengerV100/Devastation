import React, { useEffect, useState } from 'react';
import * as PIXI from 'pixi.js';
import { Stage, Container, Sprite, Text, Graphics } from '@pixi/react';
import { TextStyle } from 'pixi.js';

const GameCanvas = (props) => {
    const spriteSize = 80;

    const projectManagerImage = 'https://static.vecteezy.com/system/resources/previews/028/652/011/original/pixel-art-student-character-png.png';
    const testerImage = 'https://static.vecteezy.com/system/resources/previews/027/190/731/original/pixel-art-black-t-shirt-man-character-png.png'
    const developerImage = 'https://static.vecteezy.com/system/resources/previews/027/190/803/original/pixel-art-female-teacher-character-png.png'

    const [projectManagerX, setProjectManagerX] = useState(0);
    const [projectManagerY, setProjectManagerY] = useState(0);

    const [windowWidth, setWindowWidth] = useState(window.innerWidth);
    const [windowHeight, setWindowHeight] = useState(window.innerHeight);
    const blurFilter = new PIXI.filters.BlurFilter();

    useEffect(() => {

    }, [props.gameState]);

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
        <Stage width={windowWidth} height={windowHeight-7}>
            <Graphics
                draw={drawBackground}
                zIndex={-1} // Ensure the background is behind other elements
            />
            <Sprite
                image={projectManagerImage}
                x={projectManagerX}
                y={projectManagerY}
                width={spriteSize}
                height={spriteSize}
            />

        </Stage>
    );
};

export default GameCanvas;
