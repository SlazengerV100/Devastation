import { useEffect, useState } from 'react';
import { Stage, Sprite } from '@pixi/react';
import * as PIXI from 'pixi.js';
import { fetchPlayButton } from './js/spriteFrameGrabber.js';

// Function to fetch the background texture
const fetchBackgroundTexture = () => {
    return PIXI.Texture.from('../assets/background.png');
}

const StyledHomeScreen = () => {
    const [playButtonTexture, setPlayButtonTexture] = useState(null);
    const [backgroundTexture, setBackgroundTexture] = useState(null);

    useEffect(() => {
        // Fetch the textures for the button and background
        setPlayButtonTexture(fetchPlayButton());
        setBackgroundTexture(fetchBackgroundTexture());
    }, []);

    const handleMouseOver = (e) => {
        setPlayButtonTexture(fetchPlayButton(true)) // Change tint color to grey on hover
    };

    const handleMouseOut = (e) => {
        setPlayButtonTexture(fetchPlayButton(false)) // Reset tint color
    };

    return (
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
                {playButtonTexture && (
                    <Sprite
                        texture={playButtonTexture}
                        x={(window.innerWidth - playButtonTexture.width) / 2}
                        y={(window.innerHeight - playButtonTexture.height) / 2}
                        interactive
                        buttonMode
                        pointerover={handleMouseOver}
                        pointerout={handleMouseOut}
                    />
                )}
            </Stage>
    );
};

export default StyledHomeScreen;
