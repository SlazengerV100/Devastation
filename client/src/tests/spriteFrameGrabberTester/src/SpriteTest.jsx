import React from 'react';
import { Stage, Sprite } from '@pixi/react';
import { fetchIdle } from '../../../js/spriteFrameGrabber.js'; // Assuming spriteFrameGrabber.js is in the same directory

const SpriteTest = () => {
    const texture = fetchIdle("UP");  // You can change the direction to "UP", "LEFT", "RIGHT", "DOWN"

    return (
        <Stage width={800} height={600} options={{ backgroundColor: 0xeef1f5 }}>
            <Sprite texture={texture} x={400} y={300} anchor={0.5} />
        </Stage>
    );
};

export default SpriteTest;
