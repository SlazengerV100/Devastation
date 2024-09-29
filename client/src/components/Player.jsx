import { useEffect, useRef } from 'react';
import { AnimatedSprite } from '@pixi/react';
import { textures, TILE_WIDTH } from "../js/spriteFrameGrabber.js";

// eslint-disable-next-line react/prop-types
const Player = ({ player, mapPosition }) => {
    const spriteRef = useRef(null);

    // Use useEffect to restart the animation with new loaded textures
    useEffect(() => {
        if (spriteRef.current) {
            spriteRef.current.textures = textures.running[player.direction];
            if (!spriteRef.current.playing) spriteRef.current.gotoAndPlay(0);
        }
    }, [player.direction]);

    // Adjust player's position relative to the centered map
    const playerPositionX = mapPosition.x + (player.x * TILE_WIDTH);
    const playerPositionY = mapPosition.y + (player.y * TILE_WIDTH) - TILE_WIDTH;

    console.log(player.x + " " + player.y)

    return (
        <AnimatedSprite
            ref={spriteRef}
            key={player.id}
            isPlaying={true}
            textures={textures.running[player.direction]} // Use the correct direction for textures
            animationSpeed={0.15}
            x={playerPositionX}
            y={playerPositionY}
        />
    );
};

export default Player;
