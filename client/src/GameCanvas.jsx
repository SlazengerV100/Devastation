import React, { useEffect, useState } from 'react';
import * as PIXI from 'pixi.js';
import { Stage, Container, Sprite, Text, Graphics } from '@pixi/react';
import { TextStyle } from 'pixi.js';

const GameCanvas = ({ state }) => {
    const [bunnyX, setBunnyX] = useState(0);
    const [bunnyY, setBunnyY] = useState(0);
    const [windowWidth, setWindowWidth] = useState(window.innerWidth);
    const [windowHeight, setWindowHeight] = useState(window.innerHeight);
    const blurFilter = new PIXI.filters.BlurFilter();

    useEffect(() => {
        if (state == null) return;

        // Update ball coordinates from state
        setBunnyX(state.x);
        setBunnyY(state.y);
    }, [state]);

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

    const bunnyUrl = 'https://pixijs.io/pixi-react/img/bunny.png';

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
            <Sprite image={bunnyUrl} x={bunnyX} y={bunnyY} />

        </Stage>
    );
};

export default GameCanvas;
