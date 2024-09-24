import { Stage, Sprite, AnimatedSprite } from '@pixi/react';
import { useAtomValue } from 'jotai';
import { playerMap, localPlayerId } from "../js/atoms.js";
import map from '../../assets/map.png'; // Map image asset
import { textures } from '../js/spriteFrameGrabber.js';

const GameStage = () => {
    const players = useAtomValue(playerMap);
    const localPlayerIdValue = useAtomValue(localPlayerId);
    const localCharacter = players[localPlayerIdValue];

    const boardWidth = 30;
    const boardHeight = 15;
    const tilePixelWidth = 50;

    const mapPixelWidth = boardWidth * tilePixelWidth;
    const mapPixelHeight = boardHeight * tilePixelWidth;

    return (
        <Stage
            options={{ backgroundColor: 0x1099bb }}
            width={window.innerWidth}
            height={window.innerHeight}
        >
            {/* Render the map */}
            <Sprite
                image={map}
                width={mapPixelWidth}
                height={mapPixelHeight}
                x={0}
                y={0}
            />

            {/* Render all players */}
            {Object.values(players).map((player) => (
                <AnimatedSprite
                    key={player.id} // Ensure unique key for each player
                    isPlaying={true}
                    textures={textures.running[player.direction]} // Use the direction from the player
                    animationSpeed={0.15}
                    x={player.x * tilePixelWidth}
                    y={player.y * tilePixelWidth}
                />
            ))}
        </Stage>
    );
};

export default GameStage;


