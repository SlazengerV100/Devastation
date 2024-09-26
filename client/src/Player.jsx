import { useEffect, useRef } from 'react';
import { AnimatedSprite } from '@pixi/react';
import {textures, TILE_WIDTH} from "./js/spriteFrameGrabber.js";

// eslint-disable-next-line react/prop-types
const Player = ({ player }) => {
    const spriteRef = useRef(null);

    //Useffect here is used to restart the animation with new loaded textures
    useEffect(() => {
        if (spriteRef.current) {
            spriteRef.current.textures = textures.running[player.direction];
            if (!spriteRef.current.playing) spriteRef.current.gotoAndPlay(0);
        }
    }, [player.direction]);

    return (
        <AnimatedSprite
            ref={spriteRef}
            key={player.id}
            isPlaying={true}
            textures={textures.running[player.direction]} // Use the correct direction for textures
            animationSpeed={0.15}
            x={player.x * TILE_WIDTH}
            y={player.y * TILE_WIDTH}
        />
    );
};

export default Player;
