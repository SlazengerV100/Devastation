import { Stage, Sprite, AnimatedSprite } from '@pixi/react';
import { useAtomValue } from 'jotai';
import { playerMap, localPlayerId } from "../js/atoms.js";
import map from '../../assets/map.png'; // Map image asset
import { textures } from '../js/spriteFrameGrabber.js';

const GameStage = () => {
    const players = useAtomValue(playerMap);
    const localPlayerIdValue = useAtomValue(localPlayerId);
    const localCharacter = players[localPlayerIdValue];

    console.log(localCharacter)
    return (
        <Stage
            options={{ backgroundColor: 0x1099bb }}
            width={window.innerWidth}
            height={window.innerHeight}
        >
            {/* Render the map */}
            <Sprite
                image={map}
                x={window.innerWidth / 2}
                y={window.innerHeight / 2}
                anchor={0.5}
            />

            {/* Render the local player */}
            {localCharacter && (
                <AnimatedSprite
                    isPlaying={true}
                    textures={textures.running[localCharacter.direction]} // Use the direction from localCharacter
                    animationSpeed={0.15}
                    x={localCharacter.x} // Position based on player's x
                    y={localCharacter.y} // Position based on player's y
                    anchor={0.5}
                />
            )}
        </Stage>
    );
};

export default GameStage;

