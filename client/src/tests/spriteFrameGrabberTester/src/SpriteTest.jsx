import React, { useEffect, useState } from 'react';
import { Stage, Sprite } from '@pixi/react';
import {fetchIdle, fetchStillIdle} from '../../../js/spriteFrameGrabber.js'; // Assuming spriteFrameGrabber.js is in the same directory

const SpriteTest = () => {
    // State to hold the texture array
    const [textures, setTextures] = useState([]);  // You can change the direction to "UP", "LEFT", "RIGHT", "DOWN"
    const [frame, setFrame] = useState(0);
    const [direction, setDirection] = useState("DOWN");

    // Handle keyboard input to update direction and frame
    useEffect(() => {
        const handleKeyDown = (event) => {
            switch(event.key) {
                case "ArrowRight": {
                    setDirection("RIGHT");
                    setFrame((prevFrame) => prevFrame + 1);  // Increment frame
                    break;
                }
                case "ArrowLeft": {
                    setDirection("LEFT");
                    setFrame((prevFrame) => prevFrame - 1);  // Decrement frame
                    break;
                }
                case "ArrowUp": {
                    setDirection("UP");
                    setFrame((prevFrame) => prevFrame + 1);  // Increment frame
                    break;
                }
                case "ArrowDown": {
                    setDirection("DOWN");
                    setFrame((prevFrame) => prevFrame - 1);  // Decrement frame
                    break;
                }
                default:
                    break;
            }
        };

        window.addEventListener('keydown', handleKeyDown);

        // Cleanup event listener on component unmount
        return () => {
            window.removeEventListener('keydown', handleKeyDown);
        };
    }, []);  // Empty dependency array so this runs once when the component mounts

    // Update textures based on frame and direction changes
    useEffect(() => {
        const loadSprites = () => {
            const newTextures = [fetchStillIdle(direction)];
            newTextures.push(fetchIdle(direction,frame))
            // Fetch texture for the current direction
            setTextures(newTextures);  // Store textures in state
        };

        loadSprites();  // Call the function to load textures
    }, [frame, direction]);  // Re-run this effect when frame or direction changes

    return (
        <Stage width={800} height={500} options={{ backgroundColor: 0xeef1f5 }}>
            {textures.map((t, index) => (
                <Sprite
                    key={index}
                    texture={t}
                    x={index * 60 + 30}
                    y={300}
                />
            ))}
        </Stage>
    );
};

export default SpriteTest;
